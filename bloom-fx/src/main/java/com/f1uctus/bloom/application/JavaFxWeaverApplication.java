package com.f1uctus.bloom.application;

import com.f1uctus.bloom.application.application.MainApplication;
import com.f1uctus.bloom.core.persistence.PersistenceConfiguration;
import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.core.persistence.repositories.UserRepository;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Set;

@SpringBootApplication(scanBasePackages = "com.f1uctus.bloom")
@Import(PersistenceConfiguration.class)
public class JavaFxWeaverApplication {
    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
    }

    @Bean
    CommandLineRunner testDataInitializer(UserRepository users) {
        return args -> users.findByLogin("f1uctus@mail.ru").blockOptional()
            .orElseGet(() -> users.save(new User()
                .setLogin("f1uctus@mail.ru")
                .setPassword("123321")
                .setKeyPhrase("илья")
                .setRoles(Set.of(User.Role.SUBSCRIBER))
            ).block());
    }
}
