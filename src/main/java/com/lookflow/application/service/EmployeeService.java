package com.lookflow.application.service;

import com.lookflow.application.port.input.*;
import com.lookflow.application.port.output.EmployeeRepositoryPort;
import com.lookflow.application.port.output.ServiceRepositoryPort;
import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.exception.WorkshiftOverlapedException;
import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Address;
import com.lookflow.domain.model.valueobject.Email;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.WorkShift;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService implements RegisterEmployeeUseCase, UpdateEmployeeUseCase, 
        ManageEmployeeWorkShiftsUseCase, QueryEmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepository;
    private final ServiceRepositoryPort serviceRepository;

    public EmployeeService(EmployeeRepositoryPort employeeRepository, 
                          ServiceRepositoryPort serviceRepository) {
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Employee registerEmployee(String name, String firstSurname, String secondSurname, 
                                   Email email, Address address, List<WorkShift> workShifts,
                                   List<Service> services) {
        
        // Validar que el email no esté ya registrado
        if (isEmailAlreadyRegistered(email)) {
            throw new DomainException("Employee with email " + email + " already exists");
        }
        
        // Validar que todos los servicios existen y están activos
        for (Service service : services) {
            if (!serviceRepository.existsById(service.getId())) {
                throw new DomainException("Service not found with ID: " + service.getId());
            }
        }

        Employee employee = new Employee(name, firstSurname, secondSurname, email, address, workShifts, services);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(EmployeeId employeeId, String name, String firstSurname, 
                                 String secondSurname, Email email, Address address,
                                 List<WorkShift> workShifts, List<Service> services) {
        
        // Validar que el empleado existe
        getEmployeeByIdOrThrow(employeeId);
        
        // Validar que el nuevo email no esté ya registrado por otro empleado
        if (isEmailAlreadyRegisteredByOtherEmployee(email, employeeId)) {
            throw new DomainException("Email " + email + " is already registered by another employee");
        }
        
        // Validar que todos los servicios existen y están activos
        for (Service service : services) {
            if (!serviceRepository.existsById(service.getId())) {
                throw new DomainException("Service not found with ID: " + service.getId());
            }
        }

        Employee updatedEmployee = new Employee(name, firstSurname, secondSurname, email, address, workShifts, services);
        // Mantener el mismo ID - se maneja automáticamente por el repositorio
        
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void addWorkShift(EmployeeId employeeId, WorkShift workShift) {
        Employee employee = getEmployeeByIdOrThrow(employeeId);
        
        List<WorkShift> currentWorkShifts = new ArrayList<>(employee.getWorkShifts());
        currentWorkShifts.add(workShift);
        employee.setWorkShifts(currentWorkShifts);
        employeeRepository.save(employee);
    }

    @Override
    public void removeWorkShift(EmployeeId employeeId, WorkShift workShift) {
        Employee employee = getEmployeeByIdOrThrow(employeeId);
        
        List<WorkShift> currentWorkShifts = employee.getWorkShifts();
        currentWorkShifts.remove(workShift);
        
        employee.setWorkShifts(currentWorkShifts);
        employeeRepository.save(employee);
    }

    @Override
    public void updateWorkShifts(EmployeeId employeeId, List<WorkShift> workShifts) {
        Employee employee = getEmployeeByIdOrThrow(employeeId);
        employee.setWorkShifts(workShifts);
        employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(EmployeeId employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public List<Employee> getAvailableEmployees() {
        return employeeRepository.findAvailableEmployees();
    }

    private Employee getEmployeeByIdOrThrow(EmployeeId employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new DomainException("Employee not found with ID: " + employeeId);
        }
        return employee.get();
    }

    private boolean isEmailAlreadyRegistered(Email email) {
        return employeeRepository.existsByEmail(email);
    }

    private boolean isEmailAlreadyRegisteredByOtherEmployee(Email email, EmployeeId currentEmployeeId) {
        return employeeRepository.existsByEmailAndNotId(email, currentEmployeeId);
    }

}
