package com.lookflow.application.service;

import com.lookflow.application.port.input.*;
import com.lookflow.application.port.output.AppointmentRepositoryPort;
import com.lookflow.application.port.output.CustomerRepositoryPort;
import com.lookflow.application.port.output.EmployeeRepositoryPort;
import com.lookflow.application.port.output.ServiceRepositoryPort;
import com.lookflow.domain.exception.DateException;
import com.lookflow.domain.exception.IdException;
import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.ServiceId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AppointmentService implements CreateAppointmentUseCase, ConfirmAppointmentUseCase, 
        CancelAppointmentUseCase, CompleteAppointmentUseCase, QueryAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final CustomerRepositoryPort customerRepository;
    private final EmployeeRepositoryPort employeeRepository;
    private final ServiceRepositoryPort serviceRepository;

    public AppointmentService(AppointmentRepositoryPort appointmentRepository,
                             CustomerRepositoryPort customerRepository,
                             EmployeeRepositoryPort employeeRepository,
                             ServiceRepositoryPort serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Appointment createAppointment(CustomerId customerId, EmployeeId employeeId, 
                                       List<ServiceId> serviceIds, LocalDateTime startDate, 
                                       LocalDateTime endDate) {
        
        // Validar que el cliente existe
        if (!customerRepository.existsById(customerId)) {
            throw new IdException("Customer not found with ID: " + customerId);
        }
        
        // Validar que el empleado existe
        if (!employeeRepository.existsById(employeeId)) {
            throw new IdException("Employee not found with ID: " + employeeId);
        }
        
        // Validar que todos los servicios existen y están activos
        for (ServiceId serviceId : serviceIds) {
            Optional<Service> service = serviceRepository.findById(serviceId);
            if (service.isEmpty()) {
                throw new IdException("Service not found with ID: " + serviceId);
            }
            if (!service.get().isActive()) {
                throw new IdException("Service is not active: " + serviceId);
            }
        }
        
        // Validar que la fecha de inicio no sea en el pasado
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new DateException("Appointment start date cannot be in the past");
        }
        
        // Validar que no hay conflictos de horario con otras citas del empleado
        List<Appointment> existingAppointments = appointmentRepository.findByEmployeeId(employeeId);
        for (Appointment existingAppointment : existingAppointments) {
            if (isTimeConflict(startDate, endDate, existingAppointment.getStartDate(), 
                             existingAppointment.getEndDate())) {
                throw new DateException("Time conflict with existing appointment");
            }
        }
        
        Appointment appointment = new Appointment(customerId, employeeId, serviceIds, startDate, endDate);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void confirmAppointment(AppointmentId appointmentId) {
        Appointment appointment = getAppointmentByIdOrThrow(appointmentId);
        appointment.getAppointmentState().confirm(appointment);
        appointmentRepository.save(appointment);
    }

    @Override
    public void cancelAppointment(AppointmentId appointmentId) {
        Appointment appointment = getAppointmentByIdOrThrow(appointmentId);
        appointment.getAppointmentState().cancel(appointment);
        appointmentRepository.save(appointment);
    }

    @Override
    public void completeAppointment(AppointmentId appointmentId) {
        Appointment appointment = getAppointmentByIdOrThrow(appointmentId);
        appointment.getAppointmentState().complete(appointment);
        appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(AppointmentId appointmentId) {
        return getAppointmentByIdOrThrow(appointmentId);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomer(CustomerId customerId) {
        return appointmentRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Appointment> getAppointmentsByEmployee(EmployeeId employeeId) {
        return appointmentRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Appointment> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByDateRange(startDate, endDate);
    }

    private Appointment getAppointmentByIdOrThrow(AppointmentId appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isEmpty()) {
            throw new IdException("Appointment not found with ID: " + appointmentId);
        }
        return appointment.get();
    }

    private boolean isTimeConflict(LocalDateTime start1, LocalDateTime end1, 
                                 LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}
