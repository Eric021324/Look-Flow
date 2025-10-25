package com.lookflow.domain.model.entity;

import com.lookflow.domain.exception.DateException;
import com.lookflow.domain.exception.IdException;
import com.lookflow.domain.model.valueobject.appointmentstate.AppointmentState;
import com.lookflow.domain.model.valueobject.appointmentstate.PendingState;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.ServiceId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Appointment extends AggregateRoot<AppointmentId> {

    private final CustomerId customerId;
    private EmployeeId employeeId;
    private List<ServiceId>  serviceIds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private AppointmentState appointmentState;

    public Appointment(CustomerId customerId, EmployeeId employeeId, List<ServiceId> serviceIds,
                       LocalDateTime startDate, LocalDateTime endDate) {
        super(new AppointmentId(UUID.randomUUID()));
        if(customerId == null) throw new IdException("Customer cannot be null");
        this.customerId = customerId;
        setEmployeeId(employeeId);
        setServiceIds(serviceIds);
        setStartAndEndDate(startDate, endDate);
        setState(new PendingState());
    }

    public void setState(AppointmentState appointmentState) {
        if(appointmentState == null) throw new IllegalStateException("State cannot be null");
        this.appointmentState = appointmentState;
    }

    public void confirm(){
        appointmentState.confirm(this);
    }

    public void cancel(){
        appointmentState.cancel(this);
    }

    public void complete(){
        appointmentState.complete(this);
    }

    private void setCustomerId(CustomerId customerId) {

    }

    private void setServiceIds(List<ServiceId> serviceIds) {
        if(serviceIds == null) throw new  IllegalArgumentException("Service Ids cannot be null");
        this.serviceIds = serviceIds;
    }

    private void setEmployeeId(EmployeeId employeeId) {
        if(employeeId == null) throw new IdException("Employee Id cannot be null");
        this.employeeId = employeeId;
    }

    private void setStartAndEndDate(LocalDateTime startDate,LocalDateTime endDate){
        if(startDate==null || endDate==null) throw new DateException("The start and end dates cannot be null");
        if(endDate.isBefore(startDate)) throw new DateException("The end date cannot be before start date");
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public List<ServiceId> getServiceIds() {
        return serviceIds;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public AppointmentState getAppointmentState() {
        return appointmentState;
    }


}
