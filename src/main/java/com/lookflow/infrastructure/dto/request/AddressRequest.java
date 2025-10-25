package com.lookflow.infrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Address information")
public class AddressRequest {

    @NotBlank(message = "Street is required")
    @Schema(description = "Street address", example = "123 Main St")
    private String street;

    @NotBlank(message = "City is required")
    @Schema(description = "City", example = "New York")
    private String city;

    @NotBlank(message = "State is required")
    @Schema(description = "State", example = "NY")
    private String state;

    @NotBlank(message = "Postal code is required")
    @Schema(description = "Postal code", example = "10001")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Schema(description = "Country", example = "USA")
    private String country;

    // Constructors
    public AddressRequest() {}

    public AddressRequest(String street, String city, String state, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    // Getters and Setters
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
