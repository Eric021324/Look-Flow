package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;

public interface CancelAppointmentUseCase {
    
    Appointment cancelAppointment(AppointmentId appointmentId);
}
