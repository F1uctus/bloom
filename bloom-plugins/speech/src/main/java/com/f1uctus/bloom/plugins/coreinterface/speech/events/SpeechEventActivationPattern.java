package com.f1uctus.bloom.plugins.coreinterface.speech.events;

import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import lombok.*;

import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public final class SpeechEventActivationPattern implements ActivationPattern<SpeechEventActivationPattern, SpeechEvent> {
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
