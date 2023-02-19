package com.f1uctus.bloom.plugins.coreinterface.actions;

import com.f1uctus.bloom.plugins.coreinterface.BasePlugin;

public interface ActionPlugin<P extends ActionPayloadPattern> extends BasePlugin {
    Class<P> getPayloadClass();

    P payloadTemplate();

    default boolean supports(ActionPayloadPattern pattern) {
        return getPayloadClass().isInstance(pattern);
    }

    void execute(P pattern);
}
