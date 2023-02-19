package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPayloadPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Action extends PropertiedEntity<ActionPayloadPattern> {
    @JsonIgnore
    @ManyToOne
    User user;

    public Action(User user) {
        this.user = user;
    }
}
