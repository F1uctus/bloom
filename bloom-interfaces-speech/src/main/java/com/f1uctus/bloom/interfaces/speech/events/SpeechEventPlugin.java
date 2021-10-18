package com.f1uctus.bloom.interfaces.speech.events;

import com.f1uctus.bloom.interfaces.ObjectPersister;
import com.f1uctus.bloom.interfaces.events.*;
import com.f1uctus.bloom.interfaces.speech.SpeechRecognizer;
import reactor.adapter.JdkFlowAdapter;

import java.util.Properties;
import java.util.concurrent.Flow;

public class SpeechEventPlugin implements EventPlugin<SpeechEvent> {
    public static ObjectPersister persister;
    public static Properties properties = new Properties();
    SpeechRecognizer recognizer;
    EventPluginHost host;

    @Override public void load(EventPluginHost host) throws Exception {
        this.host = host;
        properties.load(getClass().getResourceAsStream("/recognizer.properties"));
        recognizer = new SpeechRecognizer(properties);
    }

    @Override public Flow.Publisher<SpeechEvent> events() {
        return JdkFlowAdapter.publisherToFlowPublisher(
            recognizer.startListening().filter(e -> !e.getText().isBlank())
        );
    }

    @Override public ActivationPattern<?, SpeechEvent> patternTemplate() {
        return SpeechEventActivationPattern.template();
    }

    @Override public Properties getProperties() {
        return properties;
    }

    @Override public Object getProperty(String key) {
        return properties.get(key);
    }

    @Override public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    @Override public void setObjectPersister(ObjectPersister persister) {
        SpeechEventPlugin.persister = persister;
    }

    @Override public void close() {
        recognizer.close();
    }
}
