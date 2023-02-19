package com.f1uctus.bloom.application;

import com.f1uctus.bloom.application.controllers.welcome.WelcomeStageReady;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.pf4j.PluginManager;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApplication extends Application {
    ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder()
            .sources(JavaFxWeaverApplication.class)
            .headless(false)
            .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        context.publishEvent(new WelcomeStageReady(primaryStage));
    }

    @Override
    public void stop() {
        var pm = context.getBean(PluginManager.class);
        pm.stopPlugins();
        pm.unloadPlugins();
        context.close();
        Platform.exit();
    }
}
