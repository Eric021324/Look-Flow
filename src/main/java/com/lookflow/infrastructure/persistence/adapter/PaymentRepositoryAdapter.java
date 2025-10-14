package com.lookflow.infrastructure.persistence.adapter;

import com.lookflow.application.port.output.PaymentRepositoryPort;
import com.lookflow.domain.model.entity.Payment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.PaymentId;
import com.lookflow.infrastructure.mapper.PaymentMapper;
import com.lookflow.infrastructure.persistence.entity.PaymentEntity;
import com.lookflow.infrastructure.persistence.repository.JpaPaymentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final JpaPaymentRepository jpaPaymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentRepositoryAdapter(JpaPaymentRepository jpaPaymentRepository,
                                 PaymentMapper paymentMapper) {
        this.jpaPaymentRepository = jpaPaymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = paymentMapper.domainToEntity(payment);
        paymentEntity.setCreatedAt(LocalDateTime.now());
        paymentEntity.setUpdatedAt(LocalDateTime.now());
        
        PaymentEntity savedEntity = jpaPaymentRepository.save(paymentEntity);
        return paymentMapper.entityToDomain(savedEntity);
    }

    @Override
    public Optional<Payment> findById(PaymentId paymentId) {
        return jpaPaymentRepository.findById(paymentId.getValue())
                .map(paymentMapper::entityToDomain);
    }

    @Override
    public List<Payment> findByCustomerId(CustomerId customerId) {
        List<PaymentEntity> entities = jpaPaymentRepository.findByCustomerId(customerId.getValue());
        return entities.stream()
                .map(paymentMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Payment> findByAppointmentId(AppointmentId appointmentId) {
        List<PaymentEntity> entities = jpaPaymentRepository.findByAppointmentId(appointmentId.getValue());
        return entities.stream()
                .map(paymentMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Payment> findByDateRange(LocalDate startDate, LocalDate endDate) {
        List<PaymentEntity> entities = jpaPaymentRepository.findByDateRange(startDate, endDate);
        return entities.stream()
                .map(paymentMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Payment> findAll() {
        List<PaymentEntity> entities = jpaPaymentRepository.findAll();
        return entities.stream()
                .map(paymentMapper::entityToDomain)
                .toList();
    }

    @Override
    public void delete(PaymentId paymentId) {
        jpaPaymentRepository.deleteById(paymentId.getValue());
    }

    @Override
    public boolean existsById(PaymentId paymentId) {
        return jpaPaymentRepository.existsById(paymentId.getValue());
    }
}
