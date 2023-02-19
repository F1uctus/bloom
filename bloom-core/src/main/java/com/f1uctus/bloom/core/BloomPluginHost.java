package com.f1uctus.bloom.core;

import com.f1uctus.bloom.core.events.LoginEvent;
import com.f1uctus.bloom.core.events.WorkflowMatchEvent;
import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.core.persistence.repositories.UserRepository;
import com.f1uctus.bloom.core.persistence.repositories.WorkflowRepository;
import com.f1uctus.bloom.plugins.coreinterface.PluginHost;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPayloadPattern;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import com.f1uctus.bloom.plugins.coreinterface.events.Event;
import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityHolder;
import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("pluginHost")
@Slf4j
@RequiredArgsConstructor
public class BloomPluginHost implements PluginHost {
    final ApplicationContext context;
    final UserRepository users;
    final WorkflowRepository workflows;
    User user;

    @EventListener
    public void on(Event event) {
        workflows.findByUser(user).stream()
            .filter(w -> w.getTriggers().stream().anyMatch(t -> t.getProperties().matches(event)))
            .forEach(w -> context.publishEvent(new WorkflowMatchEvent(w)));
    }

    @SuppressWarnings("unchecked")
    @EventListener
    public void on(WorkflowMatchEvent event) {
        for (var action : event.getWorkflow().getActions()) {
            context.getBeansOfType(ActionPlugin.class).values().stream()
                .filter(ap -> ap.supports(action.getProperties()))
                .map(ap -> (ActionPlugin<ActionPayloadPattern>) ap)
                .forEach(ap -> ap.execute(action.getProperties()));
        }
    }

    @Override
    public IdentityRepository<?> getIdentityRepository() {
        return users;
    }

    @Override
    public IdentityHolder getIdentityHolder() {
        return user;
    }

    @Override
    public void setIdentityHolder(IdentityHolder holder) {
        if (holder instanceof User u) {
            this.user = u;
            System.out.println("Logged in as " + u);
            context.publishEvent(new LoginEvent(u));
        }
    }
}
