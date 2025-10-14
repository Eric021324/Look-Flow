package com.lookflow.application.port.output;

import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.valueobject.EmployeeId;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {
    
    Employee save(Employee employee);
    
    Optional<Employee> findById(EmployeeId employeeId);
    
    List<Employee> findAll();
    
    List<Employee> findByName(String name);
    
    List<Employee> findAvailableEmployees();
    
    void delete(EmployeeId employeeId);
    
    boolean existsById(EmployeeId employeeId);
}
