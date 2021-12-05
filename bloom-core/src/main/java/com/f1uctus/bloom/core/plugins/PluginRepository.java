package com.f1uctus.bloom.core.plugins;

import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import lombok.RequiredArgsConstructor;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class PluginRepository {
    final PluginManager pm;

    public List<EventPlugin<?>> findAllEventPlugins() {
        return findByType(EventPlugin.class);
    }

    public List<ActionPlugin<?>> findAllActionPlugins() {
        return findByType(ActionPlugin.class);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByType(Class<? super T> pluginClass) {
        return pm.getPlugins().stream()
            .map(PluginWrapper::getPlugin)
            .filter(pluginClass::isInstance)
            .map(p -> (T) p)
            .collect(toList());
    }
}
