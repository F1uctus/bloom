package com.f1uctus.bloom.core.plugins;

import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class PluginConfiguration {
    @Bean
    PluginManager pluginManager(
        @Value("${plugins.root}") String pluginsRoot
    ) {
        return new DefaultPluginManager(Path.of(pluginsRoot));
    }
}
