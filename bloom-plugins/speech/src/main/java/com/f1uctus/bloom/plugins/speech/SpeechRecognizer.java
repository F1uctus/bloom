package com.f1uctus.bloom.plugins.speech;

import com.f1uctus.bloom.plugins.speech.events.SpeechEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.vosk.Model;
import org.vosk.Recognizer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SpeechRecognizer implements AutoCloseable {
    private static final AudioFormat INPUT_FORMAT = new AudioFormat(
        8000.0f,
        16,
        2,
        true,
        false
    );
    private static final ObjectMapper mapper = new ObjectMapper();

    private final TargetDataLine microphone;
    private final Model model;
    private final int audioBufferSize;

    public SpeechRecognizer(Properties properties) throws LineUnavailableException, IOException {
        for (var info : AudioSystem.getMixerInfo()) {
            var m = AudioSystem.getMixer(info);
            System.out.println("Mixer: " + info.getName());
            System.out.println("  Source lines:");
            for (var lineInfo : m.getSourceLineInfo()) {
                System.out.println("  - " + lineInfo);
            }
            System.out.println("  Target lines:");
            for (var lineInfo : m.getTargetLineInfo()) {
                System.out.println("  - " + lineInfo);
            }
        }

        var modelRoot = Path.of(
            ".",
            "models",
            properties.getProperty(RecognizerProperties.MODEL_NAME)
        ).normalize();
        if (!Files.isDirectory(modelRoot)) {
            modelRoot = Archives.unzip(
                SpeechPlugin.class.getResourceAsStream(
                    "/" + properties.getProperty(RecognizerProperties.MODEL_NAME) + ".zip"
                ),
                modelRoot.getParent()
            );
        }
        model = new Model(modelRoot.toAbsolutePath().toString());

        audioBufferSize = Integer.parseInt(properties.getProperty(RecognizerProperties.AUDIO_BUFFER_SIZE));
        microphone = (TargetDataLine) AudioSystem.getLine(
            new DataLine.Info(TargetDataLine.class, INPUT_FORMAT)
        );
        microphone.open(INPUT_FORMAT);
    }

    public Flux<SpeechEvent> startListening() {
        microphone.flush();
        microphone.start();
        return Flux.generate(
            () -> new Recognizer(model, INPUT_FORMAT.getSampleRate() * INPUT_FORMAT.getChannels()),
            (Recognizer recognizer, SynchronousSink<SpeechEvent> sink) -> {
                assert recognizer != null;
                try {
                    var buffer = new byte[audioBufferSize];
                    var bufLen = microphone.read(buffer, 0, buffer.length);
                    if (recognizer.acceptWaveForm(buffer, bufLen)) {
                        var result = recognizer.getResult();
                        var tree = mapper.readTree(result);
                        var text = tree.get("text");
                        sink.next(SpeechEvent.text(
                            text == null ? "" : encode(text.textValue())
                        ));
                    } else {
                        var result = recognizer.getPartialResult();
                        var tree = mapper.readTree(result);
                        var text = tree.get("partial");
                        sink.next(SpeechEvent.partial(
                            text == null ? "" : encode(text.textValue())
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sink.error(e);
                }
                return recognizer;
            },
            recognizer -> {
                if (recognizer != null) {
                    recognizer.close();
                }
            }
        ).onBackpressureDrop().distinctUntilChanged();
    }

    private static String encode(String input) {
        return new String(input.getBytes(), UTF_8);
    }

    @Override
    public void close() {
        microphone.close();
        model.close();
    }
}