package com.f1uctus.bloom.core.persistence.repositories;

import com.f1uctus.bloom.core.persistence.models.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TriggerRepository extends JpaRepository<Trigger, UUID> {
}
