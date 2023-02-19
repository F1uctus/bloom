package com.f1uctus.bloom.plugins.speech.events;

import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import com.f1uctus.bloom.plugins.coreinterface.events.Event;
import lombok.*;

import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class SpeechEventActivationPattern implements ActivationPattern<SpeechEvent> {
    Pattern textPattern;

    public static SpeechEventActivationPattern template() {
        return new SpeechEventActivationPattern(
            Pattern.compile(".*")
        );
    }

    @Override
    public String getName() {
        return "Speech";
    }

    @Override
    public boolean matches(Event event) {
        return event instanceof SpeechEvent e && textPattern.asMatchPredicate().test(e.getText());
    }

    @Override
    public String toString() {
        return getName() + ": \"" + textPattern.toString() + "\"";
    }
}
