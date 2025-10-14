package com.lookflow.domain.model.entity;

import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.exception.WorkshiftOverlapedException;
import com.lookflow.domain.model.valueobject.Address;
import com.lookflow.domain.model.valueobject.EmployeeId;
import com.lookflow.domain.model.valueobject.Rol;
import com.lookflow.domain.model.valueobject.WorkShift;

import java.util.*;

public class Employee extends AggregateRoot<EmployeeId>{

    private EmployeeId employeeId;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private Address address;
    private List<WorkShift> workShifts;
    private List<Service> services;
    private Rol rol;

    public Employee(String name, String firstSurname, String secondSurname, String email, Address adress, List<WorkShift> workShifts, List<Service> services) {
        super(new EmployeeId(UUID.randomUUID()));
        setName(name);
        setFirstSurname(firstSurname);
        setSecondSurname(secondSurname);
        setEmail(email);
        setAddress(adress);
        setWorkShift(workShifts);
        setService(services);
    }

    private void setService(List<Service> services) {
        if(services == null) throw new DomainException("Services cannot be null");
        this.services = services;
    }

    private void setWorkShift(List<WorkShift> workShifts) {
        if (workShifts == null)
            throw new DomainException("Work shift list cannot be null");

        List<WorkShift> sorted = new ArrayList<>(workShifts);
        sorted.sort(Comparator
                .comparing(WorkShift::getDayOfWeek)
                .thenComparing(WorkShift::getStartTime));

        for (int i = 1; i < sorted.size(); i++) {
            WorkShift previous = sorted.get(i - 1);
            WorkShift current = sorted.get(i);
            if (previous.getDayOfWeek().equals(current.getDayOfWeek())
                    && previous.isOverlapWith(current)) {
                throw new WorkshiftOverlapedException(
                        "Overlapping work shifts found on " + current.getDayOfWeek()
                );
            }
        }

        this.workShifts = Collections.unmodifiableList(sorted);
    }

    private void setAddress(Address address) {
        if(address == null) throw new DomainException("Adress cannot be null");
        this.address = address;
    }

    private void setEmail(String email) {

    }

    private void setSecondSurname(String secondSurname) {
        if(secondSurname == null) throw new DomainException("Second surname cannot be null");
        if(secondSurname.isEmpty()) throw new DomainException("Second surname cannot be null");
    }

    private void setFirstSurname(String firstSurname) {
        if(firstSurname == null) throw new DomainException("First surname cannot be null");
        if(firstSurname.isEmpty()) throw new DomainException("First surname cannot be empty");
    }

    private void setName(String name) {
        if(name == null) throw new DomainException("Name cannot be null");
        if(name.isEmpty()) throw new DomainException("Name cannot be empty");
        this.name=name;
    }

    public List<WorkShift> getWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(List<WorkShift> workShifts) {
        setWorkShift(workShifts);
    }
}
