package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.core.Json;
import com.f1uctus.bloom.core.plugins.PluginConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
public abstract class PropertiedEntity<T> extends AbstractPersistable<UUID> {
    String propertiesType;

    String properties;

    @Transient
    T cachedProperties;

    @Transient
    String lastState;

    @JsonIgnore
    protected String getState() {
        return Json.ser(this);
    }

    @JsonIgnore
    public boolean isChanged() {
        return !(getState().equals(lastState));
    }

    @PostLoad
    void postLoad() {
        lastState = getState();
    }

    @PrePersist
    void prePersist() {
        lastState = getState();
    }

    @PreUpdate
    void preUpdate() {
        lastState = getState();
    }

    public T getProperties() {
        if (cachedProperties == null) {
            cacheProperties();
        }
        return cachedProperties;
    }

    public void setProperties(T properties) {
        this.properties = Json.ser(properties);
        propertiesType = properties.getClass().getName();
        cachedProperties = properties;
    }

    @SuppressWarnings("unchecked")
    private void cacheProperties() {
        if (properties != null && propertiesType != null) {
            cachedProperties = (T) Json.des(
                properties,
                PluginConfiguration.findClassForName(propertiesType)
            );
        }
    }
}
