package com.lookflow.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Employee response")
public class EmployeeResponse {

    @Schema(description = "Employee ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Employee first name", example = "John")
    private String name;

    @Schema(description = "Employee first surname", example = "Doe")
    private String firstSurname;

    @Schema(description = "Employee second surname", example = "Smith")
    private String secondSurname;

    @Schema(description = "Employee email", example = "john.doe@lookflow.com")
    private String email;

    @Schema(description = "Employee address")
    private AddressResponse address;

    @Schema(description = "Employee work shifts")
    private List<WorkShiftResponse> workShifts;

    @Schema(description = "Services that the employee can provide")
    private List<UUID> serviceIds;

    @Schema(description = "Employee role", example = "STYLIST")
    private String rol;

    @Schema(description = "Creation timestamp", example = "2024-01-15T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-01-15T10:00:00")
    private LocalDateTime updatedAt;

    // Constructors
    public EmployeeResponse() {}

    public EmployeeResponse(UUID id, String name, String firstSurname, String secondSurname,
                          String email, AddressResponse address, List<WorkShiftResponse> workShifts,
                          List<UUID> serviceIds, String rol, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.email = email;
        this.address = address;
        this.workShifts = workShifts;
        this.serviceIds = serviceIds;
        this.rol = rol;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public List<WorkShiftResponse> getWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(List<WorkShiftResponse> workShifts) {
        this.workShifts = workShifts;
    }

    public List<UUID> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<UUID> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
