module com.f1uctus.bloom.application {
    requires com.f1uctus.bloom.core;
    requires com.f1uctus.bloom.interfaces;
    requires com.f1uctus.bloom.interfaces.speech;

    requires static lombok;

    requires org.reactivestreams;
    requires org.pdfsam.rxjavafx;

    requires com.fasterxml.jackson.databind;

    requires spring.context;
    requires spring.beans;
    requires spring.boot.autoconfigure;

    requires javafx.fxml;

    requires net.rgielen.fxweaver.core;
    requires net.rgielen.fxweaver.spring;
    requires spring.boot;

    exports com.f1uctus.bloom.application;
    exports com.f1uctus.bloom.application.application;
    exports com.f1uctus.bloom.application.controllers;

    opens com.f1uctus.bloom.application.controllers to javafx.fxml;
    exports com.f1uctus.bloom.application.application.events;
}