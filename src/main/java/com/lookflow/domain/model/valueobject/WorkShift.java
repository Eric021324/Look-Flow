package com.lookflow.domain.model.valueobject;

import com.lookflow.domain.exception.DomainException;


import java.time.DayOfWeek;
import java.time.LocalTime;

public class WorkShift{

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkShift(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        if(startTime.isAfter(endTime)) throw new DomainException("Start time cannot be after end time");
        setDayOfWeek(dayOfWeek);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    private void setEndTime(LocalTime endTime) {
        if(endTime == null) throw new DomainException("EndTime cannot be null");
        this.endTime = endTime;
    }

    private void setStartTime(LocalTime startTime) {
        if(startTime == null) throw new DomainException("StartTime cannot be null");
        this.startTime = startTime;
    }

    private void setDayOfWeek(DayOfWeek dayOfWeek) {
        if(dayOfWeek == null) throw new DomainException("DayOfWeek cannot be null");
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isOverlapWith(WorkShift workShift){
        if (!this.dayOfWeek.equals(workShift.dayOfWeek)) return false;
        LocalTime thisStart = this.startTime;
        LocalTime thisEnd = this.endTime;
        LocalTime otherStart = workShift.startTime;
        LocalTime otherEnd = workShift.endTime;
        return thisStart.isBefore(otherEnd) && otherStart.isBefore(thisEnd);
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
