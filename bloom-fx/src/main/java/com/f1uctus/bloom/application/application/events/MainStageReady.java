package com.f1uctus.bloom.application.application.events;

import com.f1uctus.bloom.application.controllers.MainController;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MainStageReady implements StageReadyEvent {
    Class<?> controllerClass = MainController.class;
    Stage stage;

    public MainStageReady() {
        stage = new Stage();
        stage.setTitle("Main");
    }
}
