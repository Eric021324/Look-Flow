package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.valueobject.EmployeeId;

import java.util.List;
import java.util.Optional;

public interface QueryEmployeeUseCase {
    
    Optional<Employee> getEmployeeById(EmployeeId employeeId);
    
    List<Employee> getAllEmployees();
    
    List<Employee> getEmployeesByName(String name);
    
    List<Employee> getAvailableEmployees();
}
