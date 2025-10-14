package com.lookflow.infrastructure.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EmployeeDto {

    private UUID id;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private AddressDto address;
    private List<WorkShiftDto> workShifts;
    private List<UUID> serviceIds;
    private String rol;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public EmployeeDto() {}

    public EmployeeDto(UUID id, String name, String firstSurname, String secondSurname,
                     String email, AddressDto address, List<WorkShiftDto> workShifts,
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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public List<WorkShiftDto> getWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(List<WorkShiftDto> workShifts) {
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

