package com.f1uctus.bloom.application;

import com.f1uctus.bloom.application.controllers.welcome.WelcomeStageReady;
import com.f1uctus.bloom.plugins.coreinterface.BasePlugin;
import com.f1uctus.bloom.plugins.coreinterface.PluginHost;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApplication extends Application {
    ConfigurableApplicationContext context;
    private static PluginManager pm;

    @Override public void init() {
        context = new SpringApplicationBuilder()
            .sources(JavaFxWeaverApplication.class)
            .run(getParameters().getRaw().toArray(new String[0]));

        pm = context.getBean(PluginManager.class);
        pm.loadPlugins();
        pm.startPlugins();

        var host = context.getBean(PluginHost.class);
        pm.getPlugins().stream().map(PluginWrapper::getPlugin).forEach(p -> {
            if (p instanceof BasePlugin bp) {
                bp.setHost(host);
            }
        });
    }

    @Override public void start(Stage primaryStage) {
        context.publishEvent(new WelcomeStageReady(primaryStage));
    }

    @Override public void stop() {
        context.close();
        pm.stopPlugins();
        pm.unloadPlugins();
        Platform.exit();
    }
}
