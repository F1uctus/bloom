package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityHolder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends AbstractPersistable<UUID> implements IdentityHolder {
    String firstName;

    String lastName;

    String identityHash;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    public enum Role {
        REGULAR,
        SUBSCRIBER
    }
}