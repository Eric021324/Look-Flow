package com.lookflow.infrastructure.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Request to register a new employee")
public class RegisterEmployeeRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "Employee first name", example = "John")
    private String name;

    @NotBlank(message = "First surname is required")
    @Schema(description = "Employee first surname", example = "Doe")
    private String firstSurname;

    @NotBlank(message = "Second surname is required")
    @Schema(description = "Employee second surname", example = "Smith")
    private String secondSurname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Employee email", example = "john.doe@lookflow.com")
    private String email;

    @NotNull(message = "Address is required")
    @Schema(description = "Employee address")
    private AddressRequest address;

    @NotEmpty(message = "Work shifts cannot be empty")
    @Schema(description = "Employee work shifts")
    private List<WorkShiftRequest> workShifts;

    @NotEmpty(message = "Services cannot be empty")
    @Schema(description = "Services that the employee can provide")
    private List<UUID> serviceIds;

    // Constructors
    public RegisterEmployeeRequest() {}

    public RegisterEmployeeRequest(String name, String firstSurname, String secondSurname,
                                  String email, AddressRequest address, List<WorkShiftRequest> workShifts,
                                  List<UUID> serviceIds) {
        this.name = name;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.email = email;
        this.address = address;
        this.workShifts = workShifts;
        this.serviceIds = serviceIds;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public List<WorkShiftRequest> getWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(List<WorkShiftRequest> workShifts) {
        this.workShifts = workShifts;
    }

    public List<UUID> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<UUID> serviceIds) {
        this.serviceIds = serviceIds;
    }
}
