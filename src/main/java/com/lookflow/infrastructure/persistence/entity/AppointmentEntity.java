package com.lookflow.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @ElementCollection
    @CollectionTable(name = "appointment_services", joinColumns = @JoinColumn(name = "appointment_id"))
    @Column(name = "service_id")
    private List<UUID> serviceIds;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "appointment_state", nullable = false)
    private String appointmentState;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public AppointmentEntity() {}

    public AppointmentEntity(UUID id, UUID customerId, UUID employeeId, List<UUID> serviceIds,
                            LocalDateTime startDate, LocalDateTime endDate, String appointmentState,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.serviceIds = serviceIds;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appointmentState = appointmentState;
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

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public List<UUID> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<UUID> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(String appointmentState) {
        this.appointmentState = appointmentState;
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
