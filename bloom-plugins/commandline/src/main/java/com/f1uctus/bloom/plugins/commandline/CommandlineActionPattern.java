package com.f1uctus.bloom.plugins.commandline;

import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPayloadPattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CommandlineActionPattern implements ActionPayloadPattern {
    String command;

    public static CommandlineActionPattern template() {
        return new CommandlineActionPattern(
            "notepad.exe"
        );
    }

    @Override public String getName() {
        return "Runtime command (no result)";
    }
}
