package com.lookflow.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Appointment response")
public class AppointmentResponse {

    @Schema(description = "Appointment ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Customer ID", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID customerId;

    @Schema(description = "Employee ID", example = "123e4567-e89b-12d3-a456-426614174002")
    private UUID employeeId;

    @Schema(description = "List of service IDs", example = "[\"123e4567-e89b-12d3-a456-426614174003\", \"123e4567-e89b-12d3-a456-426614174004\"]")
    private List<UUID> serviceIds;

    @Schema(description = "Appointment start date and time", example = "2024-12-25T10:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Appointment end date and time", example = "2024-12-25T11:00:00")
    private LocalDateTime endDate;

    @Schema(description = "Appointment state", example = "PENDING")
    private String appointmentState;

    @Schema(description = "Creation timestamp", example = "2024-01-15T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-01-15T10:00:00")
    private LocalDateTime updatedAt;

    // Constructors
    public AppointmentResponse() {}

    public AppointmentResponse(UUID id, UUID customerId, UUID employeeId, List<UUID> serviceIds,
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
