package com.cs246.superiorscheduling.model;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

public class ShiftTime {

    private String shiftTimeID;

    private HashSet<String> employeesOnShift;
    Date startTime;
    Date endTime;
    String parentShift;

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
    public Date getStartTime() {
        return startTime;
    }

    // StartTime Setter
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    // EndTime Getter
    public Date getEndTime() {
        return endTime;
    }

    // EndTime Setter
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    // ParentShift Getter
    public String getParentShift() {
        return parentShift;
    }

    // ParentShift Setter
    public void setParentShift(String parentShift) {
        this.parentShift = parentShift;
    }

    // Constructor
    public ShiftTime(Date startTime, Date endTime, Shift parentShift){
        this.startTime = startTime;
        this.endTime = endTime;
        this.parentShift = parentShift.getShiftID();
        this.shiftTimeID = UUID.randomUUID().toString();
    }

    // Adds employee to the shiftTime
    public void addEmployee(User user){
        this.employeesOnShift.add(user.getUserID());
    }

    public void addEmployee(String userId) { this.employeesOnShift.add(userId); }

    public void removeEmployee(String currentUserId) {
        this.employeesOnShift.remove(currentUserId);
    }
}
