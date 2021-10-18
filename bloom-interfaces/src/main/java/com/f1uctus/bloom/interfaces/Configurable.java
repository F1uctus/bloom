package com.f1uctus.bloom.interfaces;

import java.util.Properties;

public interface Configurable {
    Properties getProperties();

    Object getProperty(String key);

    void setProperty(String key, Object value);
}
