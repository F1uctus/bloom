package org.pf4j.spring;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Extension;
import org.pf4j.ExtensionFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Basic implementation of an extension factory.
 * <p><p>
 * Uses Spring's {@link AutowireCapableBeanFactory} to instantiate a given extension class.
 * <p><p>
 * Creates a new extension instance every time a request is done.
 *
 * @author Decebal Suiu
 * @author m-schroeer
 * @author Ilya Nikitin
 */
@AllArgsConstructor
@Slf4j
public class SpringExtensionFactory implements ExtensionFactory {
    final SpringPluginManager pluginManager;

    /**
     * Creates an instance of the given {@code extensionClass}. This method
     * will try to use springs autowiring possibilities.
     *
     * @param extensionClass The class annotated with {@code @}{@link Extension}.
     * @param <T>            The type for that an instance should be created.
     * @return an instance of the requested {@code extensionClass}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(final Class<T> extensionClass) {
        final var beanFactory = pluginManager.getApplicationContext()
            .getAutowireCapableBeanFactory();

        log.debug("Instantiate extension class '" + extensionClass.getName()
            + "' by using constructor autowiring.");
        // Autowire by constructor. This does not include the other types of injection (setters and/or fields).
        final Object autowiredExtension = beanFactory.autowire(
            extensionClass,
            AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR,
            // The value of the 'dependencyCheck' parameter is actually irrelevant as the using constructor of 'RootBeanDefinition'
            // skips action when the autowire mode is set to 'AUTOWIRE_CONSTRUCTOR'. Although the default value in
            // 'AbstractBeanDefinition' is 'DEPENDENCY_CHECK_NONE', so it is set to false here as well.
            false
        );
        log.trace("Created extension instance by constructor injection: " + autowiredExtension);

        log.debug("Completing autowiring of extension: " + autowiredExtension);
        // Autowire by using remaining kinds of injection (e.g. setters and/or fields).
        beanFactory.autowireBean(autowiredExtension);
        log.trace("Autowiring has been completed for extension: " + autowiredExtension);

        return (T) autowiredExtension;
    }
}
