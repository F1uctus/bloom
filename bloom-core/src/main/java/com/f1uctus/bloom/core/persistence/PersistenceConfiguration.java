package com.f1uctus.bloom.core.persistence;

import com.f1uctus.bloom.core.persistence.models.Command;
import com.f1uctus.bloom.core.persistence.models.User;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.*;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@EnableR2dbcRepositories
@EntityScan
public class PersistenceConfiguration {
    @Bean
    BeforeConvertCallback<Command<?>> commandIdGeneratingCallback(DatabaseClient dc) {
        return (entity, sqlIdentifier) -> {
            if (entity.getId() == null) {
                return dc.sql("select random_uuid()")
                    .map(row -> row.get(0, UUID.class))
                    .first()
                    .map(entity::setId);
            }
            return Mono.just(entity);
        };
    }

    @Bean
    BeforeConvertCallback<User> userIdGeneratingCallback(DatabaseClient dc) {
        return (entity, sqlIdentifier) -> {
            if (entity.getId() == null) {
                return dc.sql("select random_uuid()")
                    .map(row -> row.get(0, UUID.class))
                    .first()
                    .map(entity::setId);
            }
            return Mono.just(entity);
        };
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory cf) {
        var i = new ConnectionFactoryInitializer();
        i.setConnectionFactory(cf);
        i.setDatabasePopulator(new CompositeDatabasePopulator(
            new ResourceDatabasePopulator(new ClassPathResource("schema.sql"))
        ));
        return i;
    }
}
