package com.f1uctus.bloom.application.controllers.main;

import com.f1uctus.bloom.application.controllers.StageReadyEvent;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MainStageReady implements StageReadyEvent<MainController> {
    Class<MainController> controllerClass = MainController.class;
    Stage stage;

    @Override public void setupController(MainController controller) {
        controller.afterSetup();
    }
}
