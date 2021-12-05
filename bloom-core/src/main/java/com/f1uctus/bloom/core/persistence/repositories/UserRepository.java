package com.f1uctus.bloom.core.persistence.repositories;

import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, IdentityRepository<User> {
}
