package com.f1uctus.bloom.core.events;

import com.f1uctus.bloom.core.persistence.models.Workflow;
import lombok.Value;

@Value
public class WorkflowMatchEvent {
    Workflow workflow;
}
