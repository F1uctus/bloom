package com.f1uctus.bloom.core.persistence.repositories;

import com.f1uctus.bloom.core.persistence.models.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends R2dbcRepository<User, UUID> {
    Mono<User> findByLogin(String login);

    Flux<User> findByKeyPhrase(String keyPhrase);
}
