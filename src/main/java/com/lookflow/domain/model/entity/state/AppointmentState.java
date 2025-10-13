package com.lookflow.domain.model.entity.state;


import com.lookflow.domain.model.entity.Appointment;

public interface AppointmentState {

    public void confirm(Appointment appointment);

    public void cancel(Appointment appointment);

    public void complete(Appointment appointment);
}
