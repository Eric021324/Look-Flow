package com.lookflow.domain.model.valueobject;


import com.lookflow.domain.exception.DomainException;

import java.util.Objects;

public final class Address {

    private final String street;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String country;

    public Address(String street, String city, String state, String postalCode, String country) {
        if (street == null || street.isBlank())
            throw new DomainException("Street cannot be null or empty");
        if (city == null || city.isBlank())
            throw new DomainException("City cannot be null or empty");
        if (state == null || state.isBlank())
            throw new DomainException("State cannot be null or empty");
        if (postalCode == null || postalCode.isBlank())
            throw new DomainException("Postal code cannot be null or empty");
        if (country == null || country.isBlank())
            throw new DomainException("Country cannot be null or empty");

        this.street = street.trim();
        this.city = city.trim();
        this.state = state.trim();
        this.postalCode = postalCode.trim();
        this.country = country.trim();
    }

    public String street() {
        return street;
    }

    public String city() {
        return city;
    }

    public String state() {
        return state;
    }

    public String postalCode() {
        return postalCode;
    }

    public String country() {
        return country;
    }

    // Value Objects se comparan por valor
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address that = (Address) o;
        return street.equals(that.street)
                && city.equals(that.city)
                && state.equals(that.state)
                && postalCode.equals(that.postalCode)
                && country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, postalCode, country);
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + state + " " + postalCode + ", " + country;
    }
}