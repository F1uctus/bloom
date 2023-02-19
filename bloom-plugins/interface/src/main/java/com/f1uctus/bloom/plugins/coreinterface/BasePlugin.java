package com.f1uctus.bloom.plugins.coreinterface;

import org.pf4j.ExtensionPoint;

import java.util.Properties;

public interface BasePlugin extends ExtensionPoint {
    Properties getProperties();

    void setHost(PluginHost host);
}
