package com.f1uctus.bloom.application.controllers;

import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

public abstract class ReactiveController extends BaseController {
    protected final List<Disposable> subscriptions = new ArrayList<>();

    public void terminate() {
        subscriptions.forEach(Disposable::dispose);
        subscriptions.clear();
        super.terminate();
    }
}
