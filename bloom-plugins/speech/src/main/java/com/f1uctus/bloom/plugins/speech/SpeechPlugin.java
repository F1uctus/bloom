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
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.pf4j.*;
import reactor.adapter.JdkFlowAdapter;

import java.util.Properties;
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
        private Disposable subscription;

        public SpeechPluginImpl(PluginHost host) {
            this.host = host;
        }

        @Override
        @SneakyThrows
        public Node buildAuthorizationView() {
            var fl = new FXMLLoader(getClass().getResource("/SpeechAuthView.fxml"));
            Parent root = fl.load();
            var toggle = (ToggleButton) root.lookup("#toggle");
            var label = (Label) root.lookup("#label");
            label.managedProperty().bind(label.visibleProperty());
            label.visibleProperty().bind(toggle.selectedProperty());
            label.visibleProperty().addListener(buildUIVisibleListener(toggle, label));
            return root;
        }

        @NotNull
        private ChangeListener<Boolean> buildUIVisibleListener(
            ToggleButton authBySpeechToggle,
            Label recognizedSpeechLabel
        ) {
            return (observable, wasVisible, nowVisible) -> {
                if (subscription != null) {
                    subscription.dispose();
                    subscription = null;
                }
                if (!nowVisible)
                    return;
                subscription = Reactives.toObservable(events())
                    .subscribeOn(Schedulers.computation())
                    .filter(ev -> !ev.getText().equals(recognizedSpeechLabel.getText()))
                    .subscribe(ev -> Platform.runLater(() -> {
                        recognizedSpeechLabel.setText(ev.getText());
                        host.getIdentityRepository()
                            .findByIdentityHash(recognizedSpeechLabel.getText())
                            .ifPresentOrElse(holder -> {
                                recognizedSpeechLabel.setStyle("-fx-text-fill: green");
                                authBySpeechToggle.setSelected(false);
                                host.setIdentityHolder(holder);
                            }, () -> recognizedSpeechLabel.setStyle("-fx-text-fill: red"));
                    }));
            };
        }

        @Override
        public Flow.Publisher<SpeechEvent> events() {
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
