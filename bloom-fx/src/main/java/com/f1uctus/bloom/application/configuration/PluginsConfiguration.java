package com.f1uctus.bloom.application.configuration;

import com.f1uctus.bloom.core.BloomPluginHost;
import com.f1uctus.bloom.core.JacksonObjectPersister;
import com.f1uctus.bloom.interfaces.events.EventPluginHost;
import com.f1uctus.bloom.interfaces.speech.events.SpeechEventPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PluginsConfiguration {
    @Bean EventPluginHost eventPluginHost() {
        return new BloomPluginHost();
    }

    @Bean SpeechEventPlugin speechEventPlugin(
        EventPluginHost host
    ) throws Exception {
        var plugin = new SpeechEventPlugin();
        plugin.setObjectPersister(JacksonObjectPersister.get());
        plugin.load(host);
        return plugin;
    }
}
