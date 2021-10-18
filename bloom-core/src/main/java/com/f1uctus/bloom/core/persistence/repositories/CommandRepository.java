package com.f1uctus.bloom.core.persistence.repositories;

import com.f1uctus.bloom.core.persistence.models.Command;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface CommandRepository extends R2dbcRepository<Command<?>, UUID> {
}
