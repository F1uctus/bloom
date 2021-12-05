package com.f1uctus.bloom.plugins.coreinterface.speech.events;

import com.f1uctus.bloom.plugins.coreinterface.events.Event;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class SpeechEvent implements Event {
    String text;
    boolean partial;

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
