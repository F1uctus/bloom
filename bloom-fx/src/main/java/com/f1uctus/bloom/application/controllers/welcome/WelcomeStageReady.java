package com.f1uctus.bloom.application.controllers.welcome;

import com.f1uctus.bloom.application.controllers.StageReadyEvent;
import javafx.stage.Stage;
import lombok.Value;

@Value
public class WelcomeStageReady implements StageReadyEvent<WelcomeController> {
    Class<WelcomeController> controllerClass = WelcomeController.class;
    Stage stage;
}
