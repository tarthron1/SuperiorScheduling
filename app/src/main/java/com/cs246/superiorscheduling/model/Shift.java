package com.cs246.superiorscheduling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Shift {

    String shiftType;
    LocalDate date;
    LocalTime beginTime;
    LocalTime endTime;
    ArrayList<ShiftTime> shiftTimes;
    int requiredEmployees;

    public Shift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime){
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Shift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime, String shiftType){
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
    }

    public void addShiftTime(LocalTime startTime, LocalTime endTime){
        this.shiftTimes.add(new ShiftTime(startTime, endTime, this));
    }



}
