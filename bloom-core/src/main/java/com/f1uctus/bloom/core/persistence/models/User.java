package com.f1uctus.bloom.core.persistence.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User {
    @javax.persistence.Id @Id
    UUID id;

    String login;

    String password;

    String keyPhrase;

    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    public enum Role {
        REGULAR,
        SUBSCRIBER
    }
}