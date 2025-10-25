package com.lookflow.infrastructure.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Request to create a new appointment")
public class CreateAppointmentRequest {

    @NotNull(message = "Customer ID is required")
    @Schema(description = "Customer ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID customerId;

    @NotNull(message = "Employee ID is required")
    @Schema(description = "Employee ID", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID employeeId;

    @NotEmpty(message = "Service IDs cannot be empty")
    @Schema(description = "List of service IDs", example = "[\"123e4567-e89b-12d3-a456-426614174002\", \"123e4567-e89b-12d3-a456-426614174003\"]")
    private List<UUID> serviceIds;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    @Schema(description = "Appointment start date and time", example = "2024-12-25T10:00:00")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    @Schema(description = "Appointment end date and time", example = "2024-12-25T11:00:00")
    private LocalDateTime endDate;

    // Constructors
    public CreateAppointmentRequest() {}

    public CreateAppointmentRequest(UUID customerId, UUID employeeId, List<UUID> serviceIds, 
                                   LocalDateTime startDate, LocalDateTime endDate) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.serviceIds = serviceIds;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
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
}
