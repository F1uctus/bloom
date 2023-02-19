package org.pf4j.spring;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;

/**
 * @author Decebal Suiu
 */
@AllArgsConstructor
public class ExtensionsInjector {

    private static final Logger log = LoggerFactory.getLogger(ExtensionsInjector.class);

    protected final SpringPluginManager springPluginManager;
    protected final AbstractAutowireCapableBeanFactory beanFactory;

    public void injectExtensions() {
        // add extensions from classpath (non plugin)
        var extensionClassNames = springPluginManager.getExtensionClassNames(null);
        for (var extensionClassName : extensionClassNames) {
            try {
                log.debug("Register extension '{}' as bean", extensionClassName);
                var extensionClass = getClass().getClassLoader().loadClass(extensionClassName);
                registerExtension(extensionClass);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }

        // add extensions for each started plugin
        var startedPlugins = springPluginManager.getStartedPlugins();
        for (var plugin : startedPlugins) {
            log.debug("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
            extensionClassNames = springPluginManager.getExtensionClassNames(plugin.getPluginId());
            for (var extensionClassName : extensionClassNames) {
                try {
                    log.debug("Register extension '{}' as bean", extensionClassName);
                    var extensionClass = plugin.getPluginClassLoader()
                        .loadClass(extensionClassName);
                    registerExtension(extensionClass);
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Register an extension as bean.
     * Current implementation register extension as singleton using {@code beanFactory.registerSingleton()}.
     * The extension instance is created using {@code pluginManager.getExtensionFactory().create(extensionClass)}.
     * The bean name is the extension class name.
     * Override this method if you wish other register strategy.
     */
    protected void registerExtension(Class<?> extensionClass) {
        var extensionBeanMap = springPluginManager.getApplicationContext()
            .getBeansOfType(extensionClass);
        if (extensionBeanMap.isEmpty()) {
            var extension = springPluginManager.getExtensionFactory().create(extensionClass);
            beanFactory.registerSingleton(extensionClass.getName(), extension);
        } else {
            log.debug(
                "Bean registration aborted! Extension '{}' already existed as bean!",
                extensionClass.getName()
            );
        }
    }

}
