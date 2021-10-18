package com.f1uctus.bloom.interfaces.events;

import java.io.Externalizable;

public interface ActivationPattern<
    Self extends ActivationPattern<Self, E>,
    E extends Event
    > extends Externalizable {
    boolean matches(E event);
}
