package com.lookflow.application.port.output;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryPort {
    
    Appointment save(Appointment appointment);
    
    Optional<Appointment> findById(AppointmentId appointmentId);
    
    List<Appointment> findByCustomerId(CustomerId customerId);
    
    List<Appointment> findByEmployeeId(EmployeeId employeeId);
    
    List<Appointment> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    void delete(AppointmentId appointmentId);
    
    boolean existsById(AppointmentId appointmentId);
}
