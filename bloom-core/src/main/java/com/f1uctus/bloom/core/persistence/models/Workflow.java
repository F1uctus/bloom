package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.core.Json;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Workflow extends PropertiedEntity<Void> {
    @JsonIgnore
    @ManyToOne
    User user;

    String name;

    @OneToMany(cascade = CascadeType.ALL) //
    final List<Trigger> triggers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL) //
    final List<Action> actions = new ArrayList<>();

    public Workflow(User user, String name) {
        this.user = user;
        this.name = name;
    }

    @Override protected String getState() {
        return Json.ser(this, SkipRelations.class);
    }

    interface SkipRelations {
        @JsonIgnore
        List<Trigger> getTriggers();

        @JsonIgnore
        List<Action> getActions();
    }

    @Override public boolean isChanged() {
        return super.isChanged()
               || triggers.stream().anyMatch(PropertiedEntity::isChanged)
               || actions.stream().anyMatch(PropertiedEntity::isChanged);
    }
}
