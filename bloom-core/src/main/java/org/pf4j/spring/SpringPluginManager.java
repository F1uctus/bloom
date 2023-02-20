package org.pf4j.spring;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.pf4j.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Decebal Suiu
 * @author Ilya Nititin
 */
public class SpringPluginManager extends DefaultPluginManager implements ApplicationContextAware {
    @Getter
    ApplicationContext applicationContext;

    public SpringPluginManager(List<Path> pluginsRoots) {
        super(pluginsRoots);
    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new SpringExtensionFactory(this);
    }

    @Override
    protected PluginLoader createPluginLoader() {
        return new DefaultPluginLoader(this) {
            @Override
            public ClassLoader loadPlugin(Path pluginPath, PluginDescriptor pluginDescriptor) {
                var pcl = new PluginClassLoader(
                    pluginManager,
                    pluginDescriptor,
                    ClassLoader.getSystemClassLoader(),
                    // Important to load base (application) interfaces first
                    ClassLoadingStrategy.ADP
                );
                loadClasses(pluginPath, pcl);
                loadJars(pluginPath, pcl);
                return pcl;
            }
        };
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * This method load, start plugins and inject extensions in Spring
     */
    @PostConstruct
    public void init() {
        loadPlugins();
        startPlugins();

        var beanFactory = (AbstractAutowireCapableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        var extensionsInjector = new ExtensionsInjector(this, beanFactory);
        extensionsInjector.injectExtensions();
    }
}
