package com.f1uctus.bloom.plugins.speech;

import com.f1uctus.bloom.plugins.coreinterface.PluginHost;
import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import com.f1uctus.bloom.plugins.fxinterface.auth.AuthPlugin;
import com.f1uctus.bloom.plugins.fxinterface.common.Reactives;
import com.f1uctus.bloom.plugins.speech.events.SpeechEvent;
import com.f1uctus.bloom.plugins.speech.events.SpeechEventActivationPattern;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import lombok.SneakyThrows;
import org.pf4j.*;
import reactor.adapter.JdkFlowAdapter;

import java.util.*;
import java.util.concurrent.Flow;

public class SpeechPlugin extends Plugin {
    static final Properties properties = new Properties();
    static SpeechRecognizer recognizer;

    public SpeechPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @SneakyThrows
    @Override
    public void start() {
        properties.load(getClass().getResourceAsStream("/recognizer.properties"));
        recognizer = new SpeechRecognizer(properties);
    }

    @Override
    public void stop() {
        recognizer.close();
    }

    @Extension
    public static class SpeechPluginImpl implements EventPlugin<SpeechEvent>, AuthPlugin {
        private PluginHost host;
        private static final Map<Label, Disposable> subscriptions = new HashMap<>();

        @Override public Node buildAuthorizationView() {
            var button = new ToggleButton("Speech");
            var label = new Label();
            label.setFont(new Font(14));
            label.setText("Произнесите ключевую фразу");
            label.managedProperty().bind(label.visibleProperty());
            label.visibleProperty().bind(button.selectedProperty());
            label.visibleProperty().addListener((o, s, u) -> {
                if (u) {
                    subscriptions.put(
                        label,
                        Reactives.toObservable(events())
                            .subscribeOn(Schedulers.computation())
                            .filter(e -> !e.getText().equals(label.getText()))
                            .subscribe(e -> Platform.runLater(() -> {
                                label.setText(e.getText());
                                var holder = host.getIdentityRepository()
                                    .findByIdentityHash(label.getText());
                                if (holder.isPresent()) {
                                    label.setStyle("-fx-text-fill: green");
                                    button.setSelected(false);
                                    host.setIdentityHolder(holder.get());
                                } else {
                                    label.setStyle("-fx-text-fill: red");
                                }
                            }))
                    );
                } else if (subscriptions.containsKey(label)) {
                    subscriptions.remove(label).dispose();
                }
            });
            var box = new HBox(button, label);
            box.setAlignment(Pos.CENTER);
            box.setSpacing(10);
            box.setPadding(new Insets(5));
            return box;
        }

        @Override public Flow.Publisher<SpeechEvent> events() {
            return JdkFlowAdapter.publisherToFlowPublisher(
                recognizer.startListening().filter(e -> !e.getText().isBlank())
            );
        }

        @Override
        public ActivationPattern<SpeechEvent> patternTemplate() {
            return SpeechEventActivationPattern.template();
        }

        @Override
        public Properties getProperties() {
            return properties;
        }

        public void setHost(PluginHost host) {
            this.host = host;
        }
    }
}
