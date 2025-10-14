package com.lookflow.application.port.input;

import com.lookflow.domain.model.valueobject.AppointmentId;

public interface CancelAppointmentUseCase {
    
    void cancelAppointment(AppointmentId appointmentId);
}
