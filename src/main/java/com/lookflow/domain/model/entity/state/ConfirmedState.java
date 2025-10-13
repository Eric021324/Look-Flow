package com.lookflow.domain.model.entity.state;

import com.lookflow.domain.exception.AppointmentStateException;
import com.lookflow.domain.model.entity.Appointment;

public class ConfirmedState implements  AppointmentState{
    @Override
    public void confirm(Appointment appointment) {
        throw new AppointmentStateException("An appointment in confirmed status cannot be changed to confirmed status");
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.setState(new CancelledState());
    }

    @Override
    public void complete(Appointment appointment) {
        appointment.setState(new CompletedState());
    }
}
