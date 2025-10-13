package com.lookflow.domain.model.entity;

import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Email;

import java.time.LocalDate;
import java.util.UUID;


public class Customer extends AggregateRoot<CustomerId> {

    private String name;

    private Email email;

    private String phoneNumber;

    private final LocalDate registerDate;

    private boolean active;

    public Customer(LocalDate registerDate, String phoneNumber, Email email, String name, LocalDate registerDate1) {
        super(new CustomerId(UUID.randomUUID()));
        setActive(true);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setName(name);
        if(registerDate == null) throw new DomainException("RegisterDate cannot be null");
        this.registerDate=registerDate;
    }

    private void setName(String name) {
        if(name == null) throw new DomainException("Name cannot be null");
        if(name.isEmpty()) throw new DomainException("Name cannot be empty");
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    private void setEmail(Email email) {
        if(email == null) throw new DomainException("Email cannot be null");
        this.email = email;
    }

    private void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) throw new DomainException("PhoneNumber cannot be null");
        if(phoneNumber.isEmpty()) throw new DomainException("PhoneNumber cannot be empty");
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }
}
