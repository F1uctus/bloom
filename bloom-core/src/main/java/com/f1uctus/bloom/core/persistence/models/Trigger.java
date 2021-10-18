package com.f1uctus.bloom.core.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Trigger<T> extends PropertiedEntity<T> {
    @Id UUID id;

    String name;

    @ManyToOne
    User user;
}
