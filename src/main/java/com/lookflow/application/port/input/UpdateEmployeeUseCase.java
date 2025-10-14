package com.lookflow.application.port.input;

import com.lookflow.domain.model.entity.Employee;
import com.lookflow.domain.model.entity.Service;
import com.lookflow.domain.model.valueobject.Address;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.WorkShift;

import java.util.List;

public interface UpdateEmployeeUseCase {
    
    Employee updateEmployee(EmployeeId employeeId, String name, String firstSurname, 
                          String secondSurname, String email, Address address, 
                          List<WorkShift> workShifts, List<Service> services);
}
