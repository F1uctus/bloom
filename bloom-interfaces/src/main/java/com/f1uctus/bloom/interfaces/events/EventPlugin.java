package com.f1uctus.bloom.interfaces.events;

import com.f1uctus.bloom.interfaces.Configurable;
import com.f1uctus.bloom.interfaces.ObjectPersister;

import java.io.Closeable;
import java.util.concurrent.Flow;

public interface EventPlugin<E extends Event> extends Configurable, Closeable {
    void load(EventPluginHost host) throws Exception;

    Flow.Publisher<E> events();

    ActivationPattern<?, E> patternTemplate();

    void setObjectPersister(ObjectPersister persister);
}
