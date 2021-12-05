package com.f1uctus.bloom.plugins.coreinterface.identity;

import java.util.Optional;

public interface IdentityRepository<T extends IdentityHolder> {
    Optional<T> findByIdentityHash(String identityHash);
}
