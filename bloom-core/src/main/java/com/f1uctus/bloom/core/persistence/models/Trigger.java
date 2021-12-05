package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Trigger extends PropertiedEntity<ActivationPattern<?, ?>> {
    @JsonIgnore
    @ManyToOne
    User user;

    String name;

    public Trigger(User user, String name) {
        this.user = user;
        this.name = name;
    }
}
