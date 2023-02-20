package com.f1uctus.bloom.application.common;

import javafx.util.StringConverter;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
public class CachedBeanStringConverter<T> extends StringConverter<T> {
    final Map<String, T> cache = new HashMap<>();
    final Function<T, String> keyFunction;

    @Override
    public T fromString(String s) {
        return cache.get(s);
    }

    @Override
    public String toString(T object) {
        if (object == null) {
            return null;
        }
        var key = keyFunction.apply(object);
        cache.put(key, object);
        return key;
    }
}
