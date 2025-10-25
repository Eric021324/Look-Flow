package com.lookflow.domain.model.entity;

import com.lookflow.domain.exception.DomainException;
import com.lookflow.domain.exception.WorkshiftOverlapedException;
import com.lookflow.domain.model.valueobject.*;

import java.util.*;

public class Employee extends AggregateRoot<EmployeeId>{

    private EmployeeId employeeId;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private Email email;
    private Address address;
    private List<WorkShift> workShifts;
    private List<Service> services;
    private Rol rol;

    public Employee(String name, String firstSurname, String secondSurname, Email email, Address adress, List<WorkShift> workShifts, List<Service> services) {
        super(new EmployeeId(UUID.randomUUID()));
        setName(name);
        setFirstSurname(firstSurname);
        setSecondSurname(secondSurname);
        setEmail(email);
        setAddress(adress);
        setWorkShifts(workShifts);
        setService(services);
    }

    private void setService(List<Service> services) {
        if(services == null) throw new DomainException("Services cannot be null");
        this.services = services;
    }

    public void setWorkShifts(List<WorkShift> workShifts) {
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

    private void setEmail(Email email) {
        if(email == null) throw new DomainException("Email cannot be null");
        this.email=email;
    }

    private void setSecondSurname(String secondSurname) {
        if(secondSurname == null) throw new DomainException("Second surname cannot be null");
        if(secondSurname.isEmpty()) throw new DomainException("Second surname cannot be empty");
        this.secondSurname = secondSurname;
    }

    private void setFirstSurname(String firstSurname) {
        if(firstSurname == null) throw new DomainException("First surname cannot be null");
        if(firstSurname.isEmpty()) throw new DomainException("First surname cannot be empty");
        this.firstSurname = firstSurname;
    }

    private void setName(String name) {
        if(name == null) throw new DomainException("Name cannot be null");
        if(name.isEmpty()) throw new DomainException("Name cannot be empty");
        this.name = name;
    }

    public List<WorkShift> getWorkShifts() {
        return workShifts;
    }

    public String getName() {
        return name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public List<Service> getServices() {
        return services;
    }

    public Rol getRol() {
        return rol;
    }
}
