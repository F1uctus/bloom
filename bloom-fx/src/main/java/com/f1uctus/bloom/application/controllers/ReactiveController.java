package com.f1uctus.bloom.application.controllers;

import io.reactivex.rxjava3.disposables.Disposable;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public abstract class ReactiveController {
    @Autowired
    protected ApplicationContext context;
    protected final List<Disposable> subscriptions = new ArrayList<>();

    public abstract void initialize();

    public void terminate() {
        subscriptions.forEach(Disposable::dispose);
        subscriptions.clear();
        getStage().close();
    }

    public abstract Stage getStage();
}
