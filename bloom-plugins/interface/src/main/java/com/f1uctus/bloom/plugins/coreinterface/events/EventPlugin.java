package com.f1uctus.bloom.plugins.coreinterface.events;

import com.f1uctus.bloom.plugins.coreinterface.BasePlugin;

import java.util.concurrent.Flow;

public interface EventPlugin<E extends Event> extends BasePlugin {
    Flow.Publisher<E> events();

    ActivationPattern<E> patternTemplate();
}
