package com.f1uctus.bloom.plugins.coreinterface.events;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ActivationPattern<E extends Event> {
    @JsonIgnore
    String getName();

    boolean matches(Event event);
}
