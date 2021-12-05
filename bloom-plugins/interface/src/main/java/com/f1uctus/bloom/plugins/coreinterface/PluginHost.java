package com.f1uctus.bloom.plugins.coreinterface;

import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityHolder;
import com.f1uctus.bloom.plugins.coreinterface.identity.IdentityRepository;

public interface PluginHost {
    IdentityRepository<?> getIdentityRepository();

    IdentityHolder getIdentityHolder();

    void setIdentityHolder(IdentityHolder identityHolder);
}
