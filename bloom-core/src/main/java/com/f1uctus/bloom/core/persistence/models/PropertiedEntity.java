package com.f1uctus.bloom.core.persistence.models;

import com.f1uctus.bloom.core.Json;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.UUID;

@JsonIgnoreProperties("new")
@MappedSuperclass
@NoArgsConstructor
public abstract class PropertiedEntity<T> extends AbstractPersistable<UUID> {
    @Getter
    Class<?> propertiesType;

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
        propertiesType = properties.getClass();
        cachedProperties = properties;
    }

    @SuppressWarnings("unchecked")
    private void cacheProperties() {
        if (properties != null && propertiesType != null) {
            cachedProperties = (T) Json.des(
                properties,
                propertiesType
            );
        }
    }
}
