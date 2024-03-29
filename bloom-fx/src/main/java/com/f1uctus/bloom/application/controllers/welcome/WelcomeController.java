package com.f1uctus.bloom.application.controllers.welcome;

import com.f1uctus.bloom.application.controllers.ReactiveController;
import com.f1uctus.bloom.plugins.fxinterface.auth.AuthPlugin;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WelcomeController extends ReactiveController {
    @FXML VBox authMethods;

    public void initialize() {
        for (var ap : context.getBeansOfType(AuthPlugin.class).values()) {
            authMethods.getChildren().add(ap.buildAuthorizationView());
        }
        System.out.println("Done");
    }

    @Override
    public void terminate() {
        // Disposes all previously subscribed handlers
        authMethods.getChildren().clear();
        super.terminate();
    }

    @Override
    public Stage getStage() {
        return (Stage) authMethods.getScene().getWindow();
    }
}
