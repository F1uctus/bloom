package com.f1uctus.bloom.application.application;

import com.f1uctus.bloom.application.application.events.StageReadyEvent;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageInitializer {
    final FxWeaver fx;

    @EventListener
    public void onStageReady(StageReadyEvent event) {
        var stage = event.getStage();
        stage.setScene(new Scene(fx.loadView(event.getControllerClass())));
        stage.show();
    }
}
