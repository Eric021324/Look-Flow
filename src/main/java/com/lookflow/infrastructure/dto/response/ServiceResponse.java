package com.lookflow.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Service response")
public class ServiceResponse {

    @Schema(description = "Service ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Service name", example = "Haircut")
    private String name;

    @Schema(description = "Service description", example = "Professional haircut service")
    private String description;

    @Schema(description = "Service duration in minutes", example = "60")
    private Integer duration;

    @Schema(description = "Service cost", example = "25.00")
    private BigDecimal cost;

    @Schema(description = "Service category", example = "HAIR_CARE")
    private String serviceCategory;

    @Schema(description = "Whether the service is active", example = "true")
    private Boolean active;

    @Schema(description = "Creation timestamp", example = "2024-01-15T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-01-15T10:00:00")
    private LocalDateTime updatedAt;

    // Constructors
    public ServiceResponse() {}

    public ServiceResponse(UUID id, String name, String description, Integer duration,
                         BigDecimal cost, String serviceCategory, Boolean active,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.cost = cost;
        this.serviceCategory = serviceCategory;
        this.active = active;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
