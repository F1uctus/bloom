package com.f1uctus.bloom.core.plugins;

import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.List;

@Configuration
public class PluginConfiguration {
    private static PluginManager manager;

    @Bean
    PluginManager pluginManager(
        @Value("${plugins.root}") String pluginsRoot
    ) {
        manager = new SpringPluginManager(Path.of(pluginsRoot));
        return manager;
    }

    private static List<ClassLoader> pluginClassLoaders;

    public static Class<?> findClassForName(String name) {
        if (pluginClassLoaders == null) {
            pluginClassLoaders = manager.getPlugins().stream()
                .map(PluginWrapper::getPluginId)
                .map(id -> manager.getPluginClassLoader(id))
                .toList();
        }
        for (var cl : pluginClassLoaders) {
            try {
                return Class.forName(name, true, cl);
            } catch (ClassNotFoundException ignored) {
            }
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
