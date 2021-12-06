package com.f1uctus.bloom.core.persistence.repositories;

import com.f1uctus.bloom.core.persistence.models.Trigger;
import com.f1uctus.bloom.core.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TriggerRepository extends JpaRepository<Trigger, UUID> {
    List<Trigger> findByUser(User user);
}
