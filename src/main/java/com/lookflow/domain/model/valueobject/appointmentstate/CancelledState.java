package com.lookflow.domain.model.valueobject.appointmentstate;

import com.lookflow.domain.exception.AppointmentStateException;
import com.lookflow.domain.model.entity.Appointment;

public class CancelledState implements AppointmentState {
    @Override
    public void confirm(Appointment appointment) {
        throw new AppointmentStateException("An appointment in cancelled status cannot be changed to confirmed status");
    }

    @Override
    public void cancel(Appointment appointment) {
        throw new AppointmentStateException("An appointment in cancelled status cannot be changed to cancelled status");
    }

    @Override
    public void complete(Appointment appointment) {
        throw new AppointmentStateException("An appointment in cancelled status cannot be changed to completed status");
    }
}
