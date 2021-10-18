package com.f1uctus.bloom.interfaces;

import java.io.*;

public interface ObjectPersister {
    <T> void serialize(ObjectOutput output, T object) throws IOException;

    <T> void deserialize(ObjectInput input, T object) throws IOException;
}
