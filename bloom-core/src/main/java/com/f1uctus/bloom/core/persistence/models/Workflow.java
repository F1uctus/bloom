package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.core.Json;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Workflow<T> extends PropertiedEntity<T> {
    @JsonIgnore
    @ManyToOne
    User user;

    String name;

    @OneToMany(cascade = CascadeType.ALL) //
    final List<Trigger> triggers = new ArrayList<>();

    public Workflow(User user, String name) {
        this.user = user;
        this.name = name;
    }

    @Override protected String getState() {
        return Json.ser(this, SkipTriggers.class);
    }

    interface SkipTriggers {
        @JsonIgnore
        List<Trigger> getTriggers();
    }

    @Override public boolean isChanged() {
        return super.isChanged()
               || triggers.stream().anyMatch(PropertiedEntity::isChanged);
    }
}
