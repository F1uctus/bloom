package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Trigger extends PropertiedEntity<ActivationPattern<?>> {
    @JsonIgnore
    @ManyToOne
    User user;

    public Trigger(User user) {
        this.user = user;
    }
}
