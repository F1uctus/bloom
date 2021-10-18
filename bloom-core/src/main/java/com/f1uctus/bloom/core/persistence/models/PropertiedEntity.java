package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.core.JacksonObjectPersister;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class PropertiedEntity<T> {
    @Getter
    Class<?> propertiesType;

    String properties;

    @Transient
    T cachedProperties;

    public T getProperties() throws JsonProcessingException {
        if (cachedProperties == null) {
            cacheProperties();
        }
        return cachedProperties;
    }

    public void setProperties(T properties) throws JsonProcessingException {
        this.properties = JacksonObjectPersister.getMapper().writeValueAsString(properties);
        cachedProperties = properties;
    }

    @SuppressWarnings("unchecked")
    private void cacheProperties() throws JsonProcessingException {
        cachedProperties = (T) JacksonObjectPersister.getMapper().readValue(
            properties,
            propertiesType
        );
    }
}
