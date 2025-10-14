package com.lookflow.domain.model.entity;

import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.ServiceCategory;
import com.lookflow.domain.model.valueobject.ServiceId;

import java.util.UUID;

public class Service extends AggregateRoot<ServiceId>{

    private ServiceId serviceId;
    private String name;
    private String description;
    private int duration;
    private Money cost;
    private ServiceCategory serviceCategory;
    private boolean active;

    public Service(String name, String description, int duration, Money cost, ServiceCategory serviceCategory, boolean active) {
        super(new ServiceId(UUID.randomUUID()));
        setName(name);
        setDescription(description);
        setDuration(duration);
        setCost(cost);
        setServiceCategory(serviceCategory);
        setActive(true);
    }

    public void setActive(boolean active) {
        this.active=active;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        if(serviceCategory == null) throw new DomainException("ServiceCategory cannot be null");
        this.serviceCategory = serviceCategory;
    }

    public void setCost(Money cost) {
        if(cost == null) throw new DomainException("Money cannot be null");
        this.cost = cost;
    }

    public void setDuration(int duration) {
        if(duration <= 0) throw new DomainException("Duration of service cannot be minor than 0");
        this.duration = duration;
    }

    public void setDescription(String description) {
        if(description == null) throw new DomainException("Description cannot be null");
        this.description = description;
    }

    public void setName(String name) {
        if(name == null) throw new DomainException("Name cannot be null");
        if(name.isEmpty()) throw new DomainException("Name cannot be empty");
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }
}
