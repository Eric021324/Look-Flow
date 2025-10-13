package com.lookflow.domain.model.entity.state;

import com.lookflow.domain.exception.AppointmentStateException;
import com.lookflow.domain.model.entity.Appointment;

public class PendingState implements AppointmentState {

    @Override
    public void confirm(Appointment appointment) {
        appointment.setState(new ConfirmedState());
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.setState(new CancelledState());
    }

    @Override
    public void complete(Appointment appointment) {
        throw new AppointmentStateException("An appointment in pending status cannot be changed to completed status");
    }
}
