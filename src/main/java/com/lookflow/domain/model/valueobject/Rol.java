package com.lookflow.domain.model.valueobject;

import com.lookflow.domain.model.entity.Service;

public abstract class Rol {

    private final String name;

    protected Rol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean canProvide(ServiceCategory serviceCategory);
}
