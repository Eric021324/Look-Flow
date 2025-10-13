package com.lookflow.domain.model.valueobject;

import java.util.UUID;

public class EmployeeId extends BaseId<UUID>{

    protected EmployeeId(UUID value) {
        super(value);
    }
}
