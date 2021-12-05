package com.f1uctus.bloom.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.lang.NonNull;

public class Json {
    public static final ObjectMapper mapper = JsonMapper.builder().build();

    @NonNull
    public static String ser(
        @NonNull Object o
    ) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    public static String ser(
        @NonNull Object o,
        Class<?>... mixins
    ) {
        try {
            var mapper = Json.mapper.copy();
            for (var mixin : mixins)
                mapper.addMixIn(o.getClass(), mixin);
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    public static <T> T des(
        @NonNull String json,
        @NonNull Class<T> cls
    ) {
        try {
            return mapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
