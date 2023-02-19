package com.f1uctus.bloom.application;

import com.dooapp.fxform.adapter.*;
import com.dooapp.fxform.handler.WrappedTypeHandler;
import com.dooapp.fxform.view.factory.DefaultFactoryProvider;
import com.dooapp.fxform.view.factory.impl.TextFieldFactory;
import com.f1uctus.bloom.core.BloomPluginHost;
import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.core.persistence.repositories.UserRepository;
import com.f1uctus.bloom.plugins.coreinterface.BasePlugin;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@SpringBootApplication(scanBasePackages = "com.f1uctus.bloom", exclude = {
    R2dbcAutoConfiguration.class,
})
@EnableJpaRepositories("com.f1uctus.bloom.core.persistence.repositories")
@EntityScan(basePackages = "com.f1uctus.bloom.core.persistence.models")
public class JavaFxWeaverApplication {
    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
    }

    @DependsOn("pluginManager")
    @Bean
    CommandLineRunner pluginsHostBinder(
        BloomPluginHost host,
        List<BasePlugin> plugins
    ) {
        return args -> {
            for (var p : plugins) {
                p.setHost(host);
            }
        };
    }

    @Bean
    CommandLineRunner testDataInitializer(UserRepository users) {
        return args -> {
            if (users.count() > 0) {
                return;
            }
            users.saveAll(List.of(
                new User(
                    "Илья",
                    "Никитин",
                    "раз",
                    Set.of(User.Role.SUBSCRIBER)
                ),
                new User(
                    "Сергей",
                    "Юсовских",
                    "два",
                    Set.of(User.Role.SUBSCRIBER)
                ),
                new User(
                    "Алексий",
                    "Шагалов",
                    "три",
                    Set.of(User.Role.SUBSCRIBER)
                )
            ));
        };
    }

    @Bean
    CommandLineRunner fxFormInitializer() {
        return args -> {
            DefaultAdapterProvider.addGlobalAdapter(
                new TypeAdapterMatcher(JavaBeanObjectProperty.class, StringProperty.class),
                new PatternToStringAdapter()
            );
            DefaultFactoryProvider.addGlobalFactory(
                new WrappedTypeHandler(Pattern.class),
                new TextFieldFactory()
            );
        };
    }

    static class PatternToStringAdapter implements Adapter<Pattern, String> {
        @Override
        public String adaptTo(Pattern from) {
            return from.toString();
        }

        @Override
        public Pattern adaptFrom(String to) {
            return Pattern.compile(to);
        }
    }
}
