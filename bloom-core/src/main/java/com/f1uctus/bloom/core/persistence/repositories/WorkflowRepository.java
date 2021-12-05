package com.f1uctus.bloom.core.persistence.repositories;

import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.core.persistence.models.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkflowRepository extends JpaRepository<Workflow<?>, UUID> {
    List<Workflow<?>> findByUser(User user);
}
