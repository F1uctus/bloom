package com.f1uctus.bloom.core.persistence.repositories;

import com.f1uctus.bloom.core.persistence.models.Trigger;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

public interface TriggerRepository extends R2dbcRepository<Trigger<?>, UUID> {
}
