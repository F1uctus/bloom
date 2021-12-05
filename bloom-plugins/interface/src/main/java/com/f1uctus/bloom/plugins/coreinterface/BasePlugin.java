package com.f1uctus.bloom.plugins.coreinterface;

import java.util.Properties;

public interface BasePlugin {
    Properties getProperties();

    void setHost(PluginHost host);
}
