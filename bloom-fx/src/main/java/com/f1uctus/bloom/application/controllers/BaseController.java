package com.f1uctus.bloom.application.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class BaseController {
    @Autowired
    protected ApplicationContext context;

    public abstract void initialize();

    public void afterSetup() {
    }

    public void terminate() {
        getStage().close();
    }

    public abstract Stage getStage();

    public Scene getScene() {
        return getStage().getScene();
    }
}
