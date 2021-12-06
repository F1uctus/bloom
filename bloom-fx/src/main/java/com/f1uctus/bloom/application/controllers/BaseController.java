package com.f1uctus.bloom.application.controllers;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseController {
    @Autowired
    protected ApplicationContext context;

    public <T> List<T> getBeansOfType(Class<T> type) {
        return new ArrayList<>(context.getBeansOfType(type).values());
    }

    public abstract void initialize();

    public void afterSetup() {
        getStage().getIcons().add(new Image("/bloom.png"));
    }

    public void terminate() {
        getStage().close();
    }

    public abstract Stage getStage();

    public Scene getScene() {
        return getStage().getScene();
    }
}
