package com.f1uctus.bloom.plugins.coreinterface.actions;

import com.f1uctus.bloom.plugins.coreinterface.BasePlugin;
import org.pf4j.ExtensionPoint;

public interface ActionPlugin<P extends ActionPayloadPattern> extends ExtensionPoint, BasePlugin {
    Class<P> getPayloadClass();

    P payloadTemplate();

    default boolean supports(ActionPayloadPattern pattern) {
        return getPayloadClass().isInstance(pattern);
    }

    void execute(P pattern);
}
