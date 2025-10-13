package com.lookflow.domain.model.entity;

import com.lookflow.domain.model.valueobject.EmployeeId;

public class Employee extends AggregateRoot<EmployeeId>{

    protected Employee(EmployeeId employeeId) {
        super(employeeId);
    }
}
