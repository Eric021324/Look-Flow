package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.PaymentId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QueryPaymentUseCase {
    
    Optional<Payment> getPaymentById(PaymentId paymentId);
    
    List<Payment> getPaymentsByCustomer(CustomerId customerId);
    
    List<Payment> getPaymentsByAppointment(AppointmentId appointmentId);
    
    List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<Payment> getAllPayments();
}
