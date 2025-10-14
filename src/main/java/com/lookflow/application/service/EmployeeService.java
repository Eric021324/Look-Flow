package com.lookflow.application.service;

import com.lookflow.application.port.input.*;
import com.lookflow.application.port.output.EmployeeRepositoryPort;
import com.lookflow.application.port.output.ServiceRepositoryPort;
import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.exception.WorkshiftOverlapedException;
import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Address;
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
                                   String email, Address address, List<WorkShift> workShifts, 
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
        
        // Validar que no hay turnos superpuestos
        validateWorkShifts(workShifts);
        
        Employee employee = new Employee(name, firstSurname, secondSurname, email, address, workShifts, services);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(EmployeeId employeeId, String name, String firstSurname, 
                                 String secondSurname, String email, Address address, 
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
        
        // Validar que no hay turnos superpuestos
        validateWorkShifts(workShifts);
        
        Employee updatedEmployee = new Employee(name, firstSurname, secondSurname, email, address, workShifts, services);
        // Mantener el mismo ID - se maneja automáticamente por el repositorio
        
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void addWorkShift(EmployeeId employeeId, WorkShift workShift) {
        Employee employee = getEmployeeByIdOrThrow(employeeId);
        
        List<WorkShift> currentWorkShifts = new ArrayList<>(employee.getWorkShifts());
        currentWorkShifts.add(workShift);
        
        // Validar que no hay turnos superpuestos
        validateWorkShifts(currentWorkShifts);
        
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
        
        // Validar que no hay turnos superpuestos
        validateWorkShifts(workShifts);
        
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

    private boolean isEmailAlreadyRegistered(String email) {
        // Esta validación debería implementarse en el repositorio
        // Por ahora asumimos que no hay duplicados
        return false;
    }

    private boolean isEmailAlreadyRegisteredByOtherEmployee(String email, EmployeeId currentEmployeeId) {
        // Esta validación debería implementarse en el repositorio
        // Por ahora asumimos que no hay duplicados
        return false;
    }

    private void validateWorkShifts(List<WorkShift> workShifts) {
        if (workShifts == null || workShifts.isEmpty()) {
            return;
        }
        
        // Ordenar turnos por día de la semana y hora de inicio
        workShifts.sort((ws1, ws2) -> {
            int dayComparison = ws1.getDayOfWeek().compareTo(ws2.getDayOfWeek());
            if (dayComparison != 0) {
                return dayComparison;
            }
            return ws1.getStartTime().compareTo(ws2.getStartTime());
        });
        
        // Verificar superposiciones
        for (int i = 1; i < workShifts.size(); i++) {
            WorkShift previous = workShifts.get(i - 1);
            WorkShift current = workShifts.get(i);
            
            if (previous.getDayOfWeek().equals(current.getDayOfWeek()) && 
                previous.isOverlapWith(current)) {
                throw new WorkshiftOverlapedException(
                    "Overlapping work shifts found on " + current.getDayOfWeek()
                );
            }
        }
    }
}
