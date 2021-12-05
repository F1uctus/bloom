package com.f1uctus.bloom.core;

import com.f1uctus.bloom.core.events.LoginEvent;
import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.core.persistence.repositories.UserRepository;
import com.f1uctus.bloom.plugins.coreinterface.PluginHost;
import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityHolder;
import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BloomPluginHost implements PluginHost {
    final ApplicationContext context;
    final UserRepository users;
    User user;

    @Override public IdentityRepository<?> getIdentityRepository() {
        return users;
    }

    @Override public IdentityHolder getIdentityHolder() {
        return user;
    }

    @Override public void setIdentityHolder(IdentityHolder holder) {
        if (holder instanceof User user) {
            this.user = user;
            System.out.println("Logged in as " + user);
            context.publishEvent(new LoginEvent(user));
        }
    }
}
