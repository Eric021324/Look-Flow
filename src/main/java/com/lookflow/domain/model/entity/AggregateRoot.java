package com.lookflow.domain.model.entity;

public abstract class AggregateRoot<ID> extends BaseEntity<ID>{

    protected AggregateRoot(ID id) {
        super(id);
    }
}
