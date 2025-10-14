package com.lookflow.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "first_surname", nullable = false)
    private String firstSurname;

    @Column(name = "second_surname", nullable = false)
    private String secondSurname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Embedded
    private AddressEmbeddable address;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkShiftEntity> workShifts;

    @ElementCollection
    @CollectionTable(name = "employee_services", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "service_id")
    private List<UUID> serviceIds;

    @Column(name = "rol")
    private String rol;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public EmployeeEntity() {}

    public EmployeeEntity(UUID id, String name, String firstSurname, String secondSurname,
                        String email, AddressEmbeddable address, List<WorkShiftEntity> workShifts,
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

    public AddressEmbeddable getAddress() {
        return address;
    }

    public void setAddress(AddressEmbeddable address) {
        this.address = address;
    }

    public List<WorkShiftEntity> getWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(List<WorkShiftEntity> workShifts) {
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

