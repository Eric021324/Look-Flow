package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;

public interface ConfirmAppointmentUseCase {
    
    Appointment confirmAppointment(AppointmentId appointmentId);
}
