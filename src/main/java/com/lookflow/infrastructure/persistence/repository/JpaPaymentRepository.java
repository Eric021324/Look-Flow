package com.lookflow.infrastructure.persistence.repository;

import com.lookflow.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    List<PaymentEntity> findByCustomerId(UUID customerId);

    List<PaymentEntity> findByAppointmentId(UUID appointmentId);

    @Query("SELECT p FROM PaymentEntity p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate")
    List<PaymentEntity> findByDateRange(@Param("startDate") LocalDate startDate, 
                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM PaymentEntity p WHERE p.appointmentId = :appointmentId")
    Optional<PaymentEntity> findByAppointmentIdOptional(UUID appointmentId);
}

