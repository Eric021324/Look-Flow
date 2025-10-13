package com.lookflow.domain.model.entity.state;

import com.lookflow.domain.exception.AppointmentStateException;
import com.lookflow.domain.model.entity.Appointment;

public class CompletedState implements AppointmentState{
    @Override
    public void confirm(Appointment appointment) {
        throw new AppointmentStateException("An appointment in completed status cannot be changed to confirmed status");
    }

    @Override
    public void cancel(Appointment appointment) {
        throw new AppointmentStateException("An appointment in completed status cannot be changed to canceled status");
    }

    @Override
    public void complete(Appointment appointment) {
        throw new AppointmentStateException("An appointment in completed status cannot be changed to completed status");
    }
}
