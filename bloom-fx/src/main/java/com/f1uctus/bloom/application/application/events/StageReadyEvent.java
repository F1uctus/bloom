package com.f1uctus.bloom.application.application.events;

import javafx.stage.Stage;

public interface StageReadyEvent {
    Class<?> getControllerClass();

    Stage getStage();
}
