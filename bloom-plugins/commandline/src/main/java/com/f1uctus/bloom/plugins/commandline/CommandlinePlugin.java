package com.f1uctus.bloom.plugins.commandline;

import com.f1uctus.bloom.plugins.coreinterface.PluginHost;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import org.pf4j.*;

import java.io.IOException;
import java.util.Properties;

public class CommandlinePlugin extends Plugin {
    static final Properties properties = new Properties();

    public CommandlinePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Extension
    public static class CommandlinePluginImpl implements ActionPlugin<CommandlineActionPattern> {
        PluginHost host;

        @Override public Class<CommandlineActionPattern> getPayloadClass() {
            return CommandlineActionPattern.class;
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

        @Override public Properties getProperties() {
            return properties;
        }

        public void setHost(PluginHost host) {
            this.host = host;
        }
    }
}
