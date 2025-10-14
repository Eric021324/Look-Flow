package com.lookflow.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentDto {

    private UUID id;
    private UUID appointmentId;
    private UUID customerId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public PaymentDto() {}

    public PaymentDto(UUID id, UUID appointmentId, UUID customerId, BigDecimal amount,
                      LocalDate paymentDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentDate = paymentDate;
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

