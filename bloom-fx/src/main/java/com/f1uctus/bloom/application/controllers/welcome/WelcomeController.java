package com.f1uctus.bloom.application.controllers.welcome;

import com.f1uctus.bloom.application.controllers.ReactiveController;
import com.f1uctus.bloom.plugins.fxinterface.auth.AuthPlugin;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WelcomeController extends ReactiveController {
    @FXML AnchorPane pane;

    public void initialize() {
        for (var ap : context.getBeansOfType(AuthPlugin.class).values()) {
            pane.getChildren().add(ap.buildAuthorizationView());
        }
        System.out.println("Done");
    }

    @Override public void terminate() {
        // Disposes all previously subscribed handlers
        pane.getChildren().clear();
        super.terminate();
    }

    @Override public Stage getStage() {
        return (Stage) pane.getScene().getWindow();
    }
}
