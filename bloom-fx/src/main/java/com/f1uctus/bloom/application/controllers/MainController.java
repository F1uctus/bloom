package com.f1uctus.bloom.application.controllers;

import com.f1uctus.bloom.application.common.Reactives;
import com.f1uctus.bloom.interfaces.speech.events.SpeechEventPlugin;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainController extends ReactiveController {
    @FXML TextField commandBox;

    final SpeechEventPlugin plugin;

    @Override public void initialize() {
        subscriptions.add(Reactives.toObservable(plugin.events())
            .subscribeOn(Schedulers.io())
            .filter(e -> !e.getText().equals(commandBox.getText()))
            .observeOn(JavaFxScheduler.platform())
            .subscribe(event -> {
                var value = event.toString();
                if (value.contains("выход")) {
                    terminate();
                }
                commandBox.setText(value);
            }, System.err::println));
    }

    @Override public Stage getStage() {
        return (Stage) commandBox.getScene().getWindow();
    }
}
