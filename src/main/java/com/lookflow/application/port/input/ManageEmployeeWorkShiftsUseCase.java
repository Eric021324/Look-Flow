package com.lookflow.application.port.input;

import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.WorkShift;

import java.util.List;

public interface ManageEmployeeWorkShiftsUseCase {
    
    void addWorkShift(EmployeeId employeeId, WorkShift workShift);
    
    void removeWorkShift(EmployeeId employeeId, WorkShift workShift);
    
    void updateWorkShifts(EmployeeId employeeId, List<WorkShift> workShifts);
}
