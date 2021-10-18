module com.f1uctus.bloom.core {
    requires com.f1uctus.bloom.interfaces;

    requires static lombok;

    requires java.persistence;

    requires org.hibernate.orm.core;
    requires com.vladmihalcea.hibernate.type;

    requires spring.data.commons;
    requires spring.data.r2dbc;
    requires spring.r2dbc;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.boot.autoconfigure;

    requires com.fasterxml.jackson.databind;

    requires reactor.core;
    requires org.reactivestreams;
    requires org.slf4j;
    requires r2dbc.spi;
    requires spring.data.relational;

    opens com.f1uctus.bloom.core;
    opens com.f1uctus.bloom.core.persistence.models;
    opens com.f1uctus.bloom.core.persistence.repositories;

    exports com.f1uctus.bloom.core;
    exports com.f1uctus.bloom.core.persistence.models;
    exports com.f1uctus.bloom.core.persistence.repositories;
    exports com.f1uctus.bloom.core.persistence;
    opens com.f1uctus.bloom.core.persistence;
}