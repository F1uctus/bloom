package com.f1uctus.bloom.application.controllers;

import javafx.stage.Stage;

public interface StageReadyEvent<T extends ReactiveController> {
    Class<T> getControllerClass();

    default void setupController(T controller) {
    }

    Stage getStage();
}
