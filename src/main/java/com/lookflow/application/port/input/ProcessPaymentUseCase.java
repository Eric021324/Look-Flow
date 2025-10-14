package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Money;

import java.time.LocalDate;

public interface ProcessPaymentUseCase {
    
    Payment processPayment(AppointmentId appointmentId, CustomerId customerId, 
                          Money amount, LocalDate date);
}
