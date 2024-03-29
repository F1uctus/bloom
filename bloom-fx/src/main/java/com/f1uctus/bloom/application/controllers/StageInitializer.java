package com.f1uctus.bloom.application.controllers;

import com.f1uctus.bloom.application.controllers.main.MainStageReady;
import com.f1uctus.bloom.application.controllers.welcome.WelcomeController;
import com.f1uctus.bloom.core.events.LoginEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageInitializer {
    final ConfigurableApplicationContext context;
    FxWeaver fx;
    BaseController lastController;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @EventListener
    public void on(StageReadyEvent event) {
        var stage = event.getStage();
        if (lastController != null && lastController.getStage() == stage) {
            lastController.terminate();
        }
        if (fx == null) {
            fx = new FxWeaver(context::getBean, context::close);
        }
        var controllerAndView = fx.load(event.getControllerClass());
        stage.setScene(new Scene(
            (Parent) controllerAndView.getView().orElseThrow()
        ));
        stage.show();
        stage.toFront();
        event.setupController(
            (ReactiveController) controllerAndView.getController()
        );
        lastController = (BaseController) controllerAndView.getController();
    }

    @EventListener
    public void on(LoginEvent event) {
        if (!(lastController instanceof WelcomeController)) {
            return;
        }
        context.publishEvent(new MainStageReady(lastController.getStage()));
    }
}
