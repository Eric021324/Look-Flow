package com.lookflow.application.service;

import com.lookflow.application.port.input.*;
import com.lookflow.application.port.output.PaymentRepositoryPort;
import com.lookflow.application.port.output.AppointmentRepositoryPort;
import com.lookflow.application.port.output.CustomerRepositoryPort;
import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.Money;
import com.lookflow.domain.model.valueobject.PaymentId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService implements ProcessPaymentUseCase, QueryPaymentUseCase {

    private final PaymentRepositoryPort paymentRepository;
    private final AppointmentRepositoryPort appointmentRepository;
    private final CustomerRepositoryPort customerRepository;

    public PaymentService(PaymentRepositoryPort paymentRepository,
                         AppointmentRepositoryPort appointmentRepository,
                         CustomerRepositoryPort customerRepository) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Payment processPayment(AppointmentId appointmentId, CustomerId customerId, 
                                 Money amount, LocalDate date) {
        
        // Validar que la cita existe
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isEmpty()) {
            throw new DomainException("Appointment not found with ID: " + appointmentId);
        }
        
        // Validar que el cliente existe
        if (!customerRepository.existsById(customerId)) {
            throw new DomainException("Customer not found with ID: " + customerId);
        }
        
        // Validar que el cliente de la cita coincide con el cliente del pago
        if (!appointment.get().getCustomerId().equals(customerId)) {
            throw new DomainException("Customer ID does not match the appointment's customer");
        }
        
        // Validar que el monto sea mayor que cero
        if (!amount.isGreaterThanZero()) {
            throw new DomainException("Payment amount must be greater than zero");
        }
        
        // Validar que la fecha no sea en el futuro
        if (date.isAfter(LocalDate.now())) {
            throw new DomainException("Payment date cannot be in the future");
        }
        
        // Validar que no existe ya un pago para esta cita
        List<Payment> existingPayments = paymentRepository.findByAppointmentId(appointmentId);
        if (!existingPayments.isEmpty()) {
            throw new DomainException("Payment already exists for appointment: " + appointmentId);
        }
        
        // Crear el pago
        PaymentId paymentId = PaymentId.of(java.util.UUID.randomUUID());
        Payment payment = new Payment(paymentId, appointmentId, customerId, amount, date);
        
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(PaymentId paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getPaymentsByCustomer(CustomerId customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Payment> getPaymentsByAppointment(AppointmentId appointmentId) {
        return paymentRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        // Validar que las fechas sean válidas
        if (startDate.isAfter(endDate)) {
            throw new DomainException("Start date cannot be after end date");
        }
        
        return paymentRepository.findByDateRange(startDate, endDate);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
