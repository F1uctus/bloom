package com.f1uctus.bloom.core.events;

import com.f1uctus.bloom.core.persistence.models.User;
import lombok.Value;

@Value
public class LoginEvent {
    User user;
}
