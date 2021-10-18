package com.f1uctus.bloom.application.controllers;

import com.f1uctus.bloom.application.application.events.MainStageReady;
import com.f1uctus.bloom.application.common.Reactives;
import com.f1uctus.bloom.core.persistence.repositories.UserRepository;
import com.f1uctus.bloom.interfaces.speech.events.SpeechEventPlugin;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WelcomeController extends ReactiveController {
    @FXML Button button;
    @FXML Label label;

    final UserRepository users;
    final SpeechEventPlugin plugin;

    public void initialize() {
        subscriptions.add(Reactives.toObservable(plugin.events())
            .subscribeOn(Schedulers.io())
            .filter(e -> !e.getText().equals(label.getText()))
            .observeOn(JavaFxScheduler.platform())
            .subscribe(event -> {
                var value = event.toString();
                if (value.contains("войти")) {
                    terminate();
                    context.publishEvent(new MainStageReady());
                }
                label.setText(value);
                users.findByKeyPhrase(value).next().blockOptional().ifPresent(u -> {
                    label.setText("Welcome, " + u.getLogin());
                });
            }, System.err::println));
    }

    @Override public Stage getStage() {
        return (Stage) label.getScene().getWindow();
    }
}
