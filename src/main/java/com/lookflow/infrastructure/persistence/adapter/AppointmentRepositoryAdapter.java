package com.lookflow.infrastructure.persistence.adapter;

import com.lookflow.application.port.output.AppointmentRepositoryPort;
import com.lookflow.domain.model.entity.Appointment;
import com.lookflow.domain.model.valueobject.AppointmentId;
import com.lookflow.domain.model.valueobject.CustomerId;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.infrastructure.mapper.AppointmentMapper;
import com.lookflow.infrastructure.persistence.entity.AppointmentEntity;
import com.lookflow.infrastructure.persistence.repository.JpaAppointmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort {

    private final JpaAppointmentRepository jpaAppointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentRepositoryAdapter(JpaAppointmentRepository jpaAppointmentRepository,
                                     AppointmentMapper appointmentMapper) {
        this.jpaAppointmentRepository = jpaAppointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity appointmentEntity = appointmentMapper.domainToEntity(appointment);
        appointmentEntity.setCreatedAt(LocalDateTime.now());
        appointmentEntity.setUpdatedAt(LocalDateTime.now());
        
        AppointmentEntity savedEntity = jpaAppointmentRepository.save(appointmentEntity);
        return appointmentMapper.entityToDomain(savedEntity);
    }

    @Override
    public Optional<Appointment> findById(AppointmentId appointmentId) {
        return jpaAppointmentRepository.findById(appointmentId.getValue())
                .map(appointmentMapper::entityToDomain);
    }

    @Override
    public List<Appointment> findByCustomerId(CustomerId customerId) {
        List<AppointmentEntity> entities = jpaAppointmentRepository.findByCustomerId(customerId.getValue());
        return entities.stream()
                .map(appointmentMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Appointment> findByEmployeeId(EmployeeId employeeId) {
        List<AppointmentEntity> entities = jpaAppointmentRepository.findByEmployeeId(employeeId.getValue());
        return entities.stream()
                .map(appointmentMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Appointment> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<AppointmentEntity> entities = jpaAppointmentRepository.findByDateRange(startDate, endDate);
        return entities.stream()
                .map(appointmentMapper::entityToDomain)
                .toList();
    }

    @Override
    public void delete(AppointmentId appointmentId) {
        jpaAppointmentRepository.deleteById(appointmentId.getValue());
    }

    @Override
    public boolean existsById(AppointmentId appointmentId) {
        return jpaAppointmentRepository.existsById(appointmentId.getValue());
    }
}
