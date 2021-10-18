package com.f1uctus.bloom.interfaces.speech.events;

import com.f1uctus.bloom.interfaces.events.ActivationPattern;
import com.f1uctus.bloom.interfaces.speech.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
public final class SpeechEventActivationPattern extends AbstractPersistable implements ActivationPattern<SpeechEventActivationPattern, SpeechEvent> {
    Pattern textPattern;

    public static SpeechEventActivationPattern template() {
        return new SpeechEventActivationPattern(
            Pattern.compile(".*")
        );
    }

    @Override public boolean matches(SpeechEvent event) {
        return textPattern.asMatchPredicate().test(event.getText());
    }
}
