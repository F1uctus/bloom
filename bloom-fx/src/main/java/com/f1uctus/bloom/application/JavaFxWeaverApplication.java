package com.f1uctus.bloom.application;

import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.core.persistence.repositories.UserRepository;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Set;

@SpringBootApplication(scanBasePackages = "com.f1uctus.bloom", exclude = {
    TaskSchedulingAutoConfiguration.class,
    ApplicationAvailabilityAutoConfiguration.class,
    R2dbcAutoConfiguration.class,
    XADataSourceAutoConfiguration.class,
    CacheAutoConfiguration.class
})
@EnableJpaRepositories("com.f1uctus.bloom.core.persistence.repositories")
@EntityScan(basePackages = "com.f1uctus.bloom.core.persistence.models")
public class JavaFxWeaverApplication {
    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
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
}
