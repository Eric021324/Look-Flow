package com.lookflow.application.port.output;

import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.PaymentId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepositoryPort {
    
    Payment save(Payment payment);
    
    Optional<Payment> findById(PaymentId paymentId);
    
    List<Payment> findByCustomerId(CustomerId customerId);
    
    List<Payment> findByAppointmentId(AppointmentId appointmentId);
    
    List<Payment> findByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<Payment> findAll();
    
    void delete(PaymentId paymentId);
    
    boolean existsById(PaymentId paymentId);
}
