package com.f1uctus.bloom.plugins.coreinterface.events;

public interface ActivationPattern<
    Self extends ActivationPattern<Self, E>,
    E extends Event
    > {
    boolean matches(E event);
}
