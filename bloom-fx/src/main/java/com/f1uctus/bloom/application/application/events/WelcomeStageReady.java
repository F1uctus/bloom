package com.f1uctus.bloom.application.application.events;

import com.f1uctus.bloom.application.controllers.WelcomeController;
import javafx.stage.Stage;
import lombok.Value;

@Value
public class WelcomeStageReady implements StageReadyEvent {
    Class<?> controllerClass = WelcomeController.class;
    Stage stage;
}
