package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;

import java.time.LocalDateTime;
import java.util.List;

public interface QueryAppointmentUseCase {
    
    Appointment getAppointmentById(AppointmentId appointmentId);
    
    List<Appointment> getAppointmentsByCustomer(CustomerId customerId);
    
    List<Appointment> getAppointmentsByEmployee(EmployeeId employeeId);
    
    List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> getAllAppointments();
}
