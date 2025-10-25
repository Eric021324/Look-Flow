package com.lookflow.infrastructure.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Request to register a new customer")
public class RegisterCustomerRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "Customer name", example = "John Doe")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    @Schema(description = "Customer phone number", example = "+1234567890")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Customer email", example = "john.doe@example.com")
    private String email;

    @NotNull(message = "Register date is required")
    @Schema(description = "Customer registration date", example = "2024-01-15")
    private LocalDate registerDate;

    // Constructors
    public RegisterCustomerRequest() {}

    public RegisterCustomerRequest(String name, String phoneNumber, String email, LocalDate registerDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registerDate = registerDate;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
}
