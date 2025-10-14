package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.ServiceId;

import java.time.LocalDateTime;
import java.util.List;

public interface CreateAppointmentUseCase {
    
    Appointment createAppointment(CustomerId customerId, EmployeeId employeeId, 
                                List<ServiceId> serviceIds, LocalDateTime startDate, 
                                LocalDateTime endDate);
}
