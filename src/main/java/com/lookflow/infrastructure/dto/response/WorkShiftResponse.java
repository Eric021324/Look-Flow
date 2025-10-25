package com.lookflow.infrastructure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

@Schema(description = "Work shift information")
public class WorkShiftResponse {

    @Schema(description = "Day of the week", example = "MONDAY")
    private String dayOfWeek;

    @Schema(description = "Start time", example = "09:00:00")
    private LocalTime startTime;

    @Schema(description = "End time", example = "17:00:00")
    private LocalTime endTime;

    // Constructors
    public WorkShiftResponse() {}

    public WorkShiftResponse(String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
