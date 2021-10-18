package com.f1uctus.bloom.interfaces.speech;

import java.io.*;

import static com.f1uctus.bloom.interfaces.speech.events.SpeechEventPlugin.persister;

public abstract class AbstractPersistable implements Externalizable {
    public AbstractPersistable() {
    }

    @Override public void writeExternal(ObjectOutput out) throws IOException {
        persister.serialize(out, this);
    }

    @Override public void readExternal(ObjectInput in) throws IOException {
        persister.deserialize(in, this);
    }
}
