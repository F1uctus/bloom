package com.f1uctus.bloom.interfaces.speech.events;

import com.f1uctus.bloom.interfaces.events.Event;
import com.f1uctus.bloom.interfaces.speech.AbstractPersistable;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter @Setter
@EqualsAndHashCode
public class SpeechEvent extends AbstractPersistable implements Event {
    String text;
    boolean partial;

    public SpeechEvent() {
    }

    public static SpeechEvent text(String text) {
        return new SpeechEvent(text, false);
    }

    public static SpeechEvent partial(String text) {
        return new SpeechEvent(text, true);
    }

    @Override public String toString() {
        return text;
    }
}
