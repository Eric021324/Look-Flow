package com.lookflow.domain.model.entity;

import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.PaymentId;

import java.time.LocalDate;

public class Payment extends AggregateRoot<PaymentId>{

    private PaymentId paymentId;
    private AppointmentId appointmentId;
    private CustomerId customerId;
    private Money amount;
    private LocalDate date;

    public Payment(PaymentId paymentId, AppointmentId appointmentId, CustomerId customerId, Money amount, LocalDate date) {
        super(paymentId);
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.amount = amount;
        this.date = date;
    }

    public PaymentId getPaymentId() {
        return paymentId;
    }

    public AppointmentId getAppointmentId() {
        return appointmentId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
