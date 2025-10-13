package com.lookflow.domain.model.entity;

import com.lookflow.domain.model.valueobject.ServiceId;

public class Service extends AggregateRoot<ServiceId>{
    protected Service(ServiceId serviceId) {
        super(serviceId);
    }
}
