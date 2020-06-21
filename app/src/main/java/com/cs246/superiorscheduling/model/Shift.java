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

    // ShiftType Getter
    public String getShiftType() {
        return shiftType;
    }

    // ShiftType Setter
    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    // Date Getter
    public LocalDate getDate() {
        return date;
    }

    // Date Setter
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // BeginTime Getter
    public LocalTime getBeginTime() {
        return beginTime;
    }

    // BeginTime Setter
    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    // EndTime Getter
    public LocalTime getEndTime() {
        return endTime;
    }

    // EndTime Setter
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // ShiftTimes Getter
    public ArrayList<ShiftTime> getShiftTimes() {
        return shiftTimes;
    }

    // ShiftTimes Setter
    public void setShiftTimes(ArrayList<ShiftTime> shiftTimes) {
        this.shiftTimes = shiftTimes;
    }

    // RequiredEmployees Getter
    public int getRequiredEmployees() {
        return requiredEmployees;
    }

    // RequiredEmployees Setter
    public void setRequiredEmployees(int requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }

    // Constructor (Standard)
    public Shift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime){
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    // Constructor (Specific?)
    public Shift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime, String shiftType){
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
    }

    // Adds new ShiftTime
    public void addShiftTime(LocalTime startTime, LocalTime endTime){
        this.shiftTimes.add(new ShiftTime(startTime, endTime, this));
    }



}
