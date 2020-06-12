package com.cs246.superiorscheduling;

import java.time.LocalTime;
import java.util.HashSet;

public class ShiftTime {

    private HashSet<User> employeesOnShift;
    LocalTime startTime;
    LocalTime endTime;
    Shift parentShift;

    public ShiftTime(LocalTime startTime, LocalTime endTime, Shift parentShift){
        this.startTime = startTime;
        this.endTime = endTime;
        this.parentShift = parentShift;
    }

    public void addEmployee(User user){
        this.employeesOnShift.add(user);
    }
}
