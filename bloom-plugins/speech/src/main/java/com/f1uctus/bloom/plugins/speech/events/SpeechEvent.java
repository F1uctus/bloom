package com.f1uctus.bloom.plugins.speech.events;

import com.f1uctus.bloom.plugins.coreinterface.events.Event;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SpeechEvent implements Event {
    @EqualsAndHashCode.Include
    String text;
    boolean partial;

    public static SpeechEvent text(String text) {
        return new SpeechEvent(text, false);
    }

    public static SpeechEvent partial(String text) {
        return new SpeechEvent(text, true);
    }

    @Override
    public String toString() {
        return text;
    }
}
