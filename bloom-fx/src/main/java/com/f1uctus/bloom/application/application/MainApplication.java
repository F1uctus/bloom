package com.f1uctus.bloom.application.application;

import com.f1uctus.bloom.application.JavaFxWeaverApplication;
import com.f1uctus.bloom.application.application.events.WelcomeStageReady;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApplication extends Application {
    ConfigurableApplicationContext context;

    @Override public void init() {
        context = new SpringApplicationBuilder()
            .sources(JavaFxWeaverApplication.class)
            .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override public void start(Stage primaryStage) {
        context.publishEvent(new WelcomeStageReady(primaryStage));
    }

    @Override public void stop() {
        context.close();
        Platform.exit();
    }
}
