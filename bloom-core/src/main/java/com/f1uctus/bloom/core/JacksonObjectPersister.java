package com.f1uctus.bloom.core;

import com.f1uctus.bloom.interfaces.ObjectPersister;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class JacksonObjectPersister implements ObjectPersister {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final JacksonObjectPersister instance = new JacksonObjectPersister();

    public static JacksonObjectPersister get() {
        return instance;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    @Override public <T> void serialize(ObjectOutput output, T object) throws IOException {
        mapper.writeValue(output, object);
    }

    @Override public <T> void deserialize(ObjectInput input, T object) throws IOException {
        mapper.readerForUpdating(object).readValue(input);
    }
}
