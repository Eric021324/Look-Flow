package com.lookflow.infrastructure.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Request to process a payment")
public class ProcessPaymentRequest {

    @NotNull(message = "Appointment ID is required")
    @Schema(description = "Appointment ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID appointmentId;

    @NotNull(message = "Customer ID is required")
    @Schema(description = "Customer ID", example = "123e4567-e89b-12d3-a456-426614174001")
    private UUID customerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Schema(description = "Payment amount", example = "50.00")
    private BigDecimal amount;

    @NotNull(message = "Payment date is required")
    @Schema(description = "Payment date", example = "2024-01-15")
    private LocalDate paymentDate;

    // Constructors
    public ProcessPaymentRequest() {}

    public ProcessPaymentRequest(UUID appointmentId, UUID customerId, BigDecimal amount, LocalDate paymentDate) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and Setters
    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
