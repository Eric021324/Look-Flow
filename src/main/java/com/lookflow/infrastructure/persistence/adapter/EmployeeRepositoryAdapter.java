package com.lookflow.infrastructure.persistence.adapter;

import com.lookflow.application.port.output.EmployeeRepositoryPort;
import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.valueobject.Email;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.infrastructure.mapper.EmployeeMapper;
import com.lookflow.infrastructure.persistence.entity.EmployeeEntity;
import com.lookflow.infrastructure.persistence.repository.JpaEmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final JpaEmployeeRepository jpaEmployeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeRepositoryAdapter(JpaEmployeeRepository jpaEmployeeRepository,
                                   EmployeeMapper employeeMapper) {
        this.jpaEmployeeRepository = jpaEmployeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Employee save(Employee employee) {
        EmployeeEntity employeeEntity = employeeMapper.domainToEntity(employee);
        employeeEntity.setCreatedAt(LocalDateTime.now());
        employeeEntity.setUpdatedAt(LocalDateTime.now());
        
        EmployeeEntity savedEntity = jpaEmployeeRepository.save(employeeEntity);
        return employeeMapper.entityToDomain(savedEntity);
    }

    @Override
    public Optional<Employee> findById(EmployeeId employeeId) {
        return jpaEmployeeRepository.findById(employeeId.getValue())
                .map(employeeMapper::entityToDomain);
    }

    @Override
    public List<Employee> findAll() {
        List<EmployeeEntity> entities = jpaEmployeeRepository.findAll();
        return entities.stream()
                .map(employeeMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Employee> findByName(String name) {
        List<EmployeeEntity> entities = jpaEmployeeRepository.findByName(name);
        return entities.stream()
                .map(employeeMapper::entityToDomain)
                .toList();
    }

    @Override
    public List<Employee> findAvailableEmployees() {
        List<EmployeeEntity> entities = jpaEmployeeRepository.findAvailableEmployees();
        return entities.stream()
                .map(employeeMapper::entityToDomain)
                .toList();
    }

    @Override
    public void delete(EmployeeId employeeId) {
        jpaEmployeeRepository.deleteById(employeeId.getValue());
    }

    @Override
    public boolean existsById(EmployeeId employeeId) {
        return jpaEmployeeRepository.existsById(employeeId.getValue());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaEmployeeRepository.existsByEmail(email.getValue());
    }

    @Override
    public boolean existsByEmailAndNotId(Email email, EmployeeId currentEmployeeId) {
        return jpaEmployeeRepository.existsByEmailAndIdNot(email.getValue(), currentEmployeeId.getValue());
    }
}
