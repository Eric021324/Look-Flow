package com.lookflow.domain.model.valueobject;

import java.util.UUID;

public class CustomerId extends BaseId<UUID>
{
    public CustomerId(UUID id) {
        super(id);
    }
}
