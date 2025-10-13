package com.lookflow.domain.model.valueobject;

import java.util.UUID;

public class ServiceId extends BaseId<UUID>{
    protected ServiceId(UUID value) {
        super(value);
    }
}
