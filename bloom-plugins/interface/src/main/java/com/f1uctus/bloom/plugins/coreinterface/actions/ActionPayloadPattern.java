package com.f1uctus.bloom.plugins.coreinterface.actions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ActionPayloadPattern {
    @JsonIgnore
    String getName();
}
