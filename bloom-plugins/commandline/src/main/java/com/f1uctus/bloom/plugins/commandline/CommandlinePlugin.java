package com.f1uctus.bloom.plugins.commandline;

import com.f1uctus.bloom.plugins.coreinterface.PluginHost;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import lombok.Getter;
import lombok.Setter;
import org.pf4j.*;

import java.io.IOException;
import java.util.Properties;

@Extension
public class CommandlinePlugin extends Plugin implements ActionPlugin<CommandlineActionPattern> {
    PluginHost host;
    @Getter final Properties properties = new Properties();

    public CommandlinePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override public void start() {
    }

    @Override public void stop() {
    }

    @Override public CommandlineActionPattern payloadTemplate() {
        return CommandlineActionPattern.template();
    }

    @Override public void execute(CommandlineActionPattern pattern) {
        try {
            Runtime.getRuntime().exec(pattern.getCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHost(PluginHost host) {
        this.host = host;
    }
}
