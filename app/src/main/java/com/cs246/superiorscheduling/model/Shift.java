package com.cs246.superiorscheduling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class Shift {

    private String shiftID;
    UUID uuid = UUID.randomUUID();
    String shiftType;
    private String parentSchedule;
    LocalDate date;
    LocalTime beginTime;
    LocalTime endTime;
    ArrayList<String> shiftTimes;
    int requiredEmployees;

    public String getParentSchedule() {
        return parentSchedule;
    }

    public void setParentSchedule(String parentSchedule) {
        this.parentSchedule = parentSchedule;
    }

    // ID Getter
    public String getShiftID() {
        return shiftID;
    }

    // ID Setter
    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

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
    public ArrayList<String> getShiftTimes() {
        return shiftTimes;
    }

    // ShiftTimes Setter
    public void setShiftTimes(ArrayList<String> shiftTimes) {
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
    public Shift(Schedule parentSchedule, LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime){
        this.parentSchedule = parentSchedule.getScheduleID();
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shiftID = uuid.toString();
    }

    // Constructor (Specific?)
    public Shift(Schedule parentSchedule, LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime, String shiftType){
        this.parentSchedule = parentSchedule.getScheduleID();
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
        this.shiftID = uuid.toString();
    }

    // Adds new ShiftTime
    public void addShiftTime(ShiftTime shiftTime){
        this.shiftTimes.add(shiftTime.getShiftTimeID());
    }



}
