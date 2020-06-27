package com.cs246.superiorscheduling.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.UUID;

public class ShiftTime {

    private String shiftTimeID;
    UUID uuid = UUID.randomUUID();
    private HashSet<String> employeesOnShift;
    LocalTime startTime;
    LocalTime endTime;
    Shift parentShift;

    // ID Getter
    public String getShiftTimeID() {
        return shiftTimeID;
    }

    // ID Setter
    public void setShiftTimeID(String shiftTimeID) {
        this.shiftTimeID = shiftTimeID;
    }

    // EmployeesOnShift Getter
    public HashSet<String> getEmployeesOnShift() {
        return employeesOnShift;
    }

    // EmployeesOnShift Setter
    public void setEmployeesOnShift(HashSet<String> employeesOnShift) {
        this.employeesOnShift = employeesOnShift;
    }

    // StartTime Getter
    public LocalTime getStartTime() {
        return startTime;
    }

    // StartTime Setter
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // EndTime Getter
    public LocalTime getEndTime() {
        return endTime;
    }

    // EndTime Setter
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // ParentShift Getter
    public Shift getParentShift() {
        return parentShift;
    }

    // ParentShift Setter
    public void setParentShift(Shift parentShift) {
        this.parentShift = parentShift;
    }

    // Constructor
    public ShiftTime(LocalTime startTime, LocalTime endTime, Shift parentShift){
        this.startTime = startTime;
        this.endTime = endTime;
        this.parentShift = parentShift;
        this.shiftTimeID = uuid.toString();
    }

    // Adds employee to the shiftTime
    public void addEmployee(User user){
        this.employeesOnShift.add(user.getUserID());
    }
}
