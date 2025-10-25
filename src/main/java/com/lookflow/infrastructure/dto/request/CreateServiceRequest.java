package com.lookflow.infrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMin;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Request to create a new service")
public class CreateServiceRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "Service name", example = "Haircut")
    private String name;

    @NotBlank(message = "Description is required")
    @Schema(description = "Service description", example = "Professional haircut service")
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    @Schema(description = "Service duration in minutes", example = "60")
    private Integer duration;

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    @Schema(description = "Service cost", example = "25.00")
    private BigDecimal cost;

    @NotBlank(message = "Service category is required")
    @Schema(description = "Service category", example = "HAIR_CARE")
    private String serviceCategory;

    @Schema(description = "Whether the service is active", example = "true")
    private Boolean active = true;

    // Constructors
    public CreateServiceRequest() {}

    public CreateServiceRequest(String name, String description, Integer duration, 
                              BigDecimal cost, String serviceCategory, Boolean active) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.cost = cost;
        this.serviceCategory = serviceCategory;
        this.active = active;
    }

    // Getters and Setters
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
}
