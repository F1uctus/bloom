module com.f1uctus.bloom.interfaces.speech {
    requires com.f1uctus.bloom.interfaces;

    requires static lombok;

    requires reactor.core;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires org.vosk;

    exports com.f1uctus.bloom.interfaces.speech;
    exports com.f1uctus.bloom.interfaces.speech.events;
}