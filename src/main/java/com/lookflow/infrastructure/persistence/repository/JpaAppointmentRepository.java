package com.lookflow.infrastructure.persistence.repository;

import com.lookflow.infrastructure.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    List<AppointmentEntity> findByCustomerId(UUID customerId);

    List<AppointmentEntity> findByEmployeeId(UUID employeeId);

    @Query("SELECT a FROM AppointmentEntity a WHERE a.startDate >= :startDate AND a.endDate <= :endDate")
    List<AppointmentEntity> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM AppointmentEntity a WHERE a.employeeId = :employeeId AND " +
           "((a.startDate <= :startDate AND a.endDate > :startDate) OR " +
           "(a.startDate < :endDate AND a.endDate >= :endDate) OR " +
           "(a.startDate >= :startDate AND a.endDate <= :endDate))")
    List<AppointmentEntity> findConflictingAppointments(@Param("employeeId") UUID employeeId,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);
}

