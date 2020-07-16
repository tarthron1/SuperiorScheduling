package com.cs246.superiorscheduling.model;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Shift {

    private String shiftID;
    private String shiftType;
    private String parentSchedule;
    private Date date;
    private Date beginTime;
    private Date endTime;
    private ArrayList<String> shiftTimes;
    int requiredEmployees;

    public Shift(Date date, int requiredEmployees, Date beginTime, Date endTime) {
    }

    public Shift(Date date, int requiredEmployees, Date beginTime, Date endTime, String shiftType) {
    }

    public Shift() {

    }

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
    public Date getDate() {
        return date;
    }

    // Date Setter
    public void setDate(Date date) {
        this.date = date;
    }

    // BeginTime Getter
    public Date getBeginTime() {
        return beginTime;
    }

    // BeginTime Setter
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    // EndTime Getter
    public Date getEndTime() {
        return endTime;
    }

    // EndTime Setter
    public void setEndTime(Date endTime) {
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
    public Shift(String parentScheduleID, Date date, int requiredEmployees, Date beginTime, Date endTime){
        shiftTimes = new ArrayList<>();
        this.parentSchedule = parentScheduleID;
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shiftID = UUID.randomUUID().toString();
    }

    // Constructor (Specific?)
    public Shift(String parentScheduleID, Date date, int requiredEmployees, Date beginTime, Date endTime, String shiftType){
        shiftTimes = new ArrayList<>();
        this.parentSchedule = parentScheduleID;
        this.date = date;
        this.requiredEmployees = requiredEmployees;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
        this.shiftID = UUID.randomUUID().toString();
    }

    // Adds new ShiftTime
    public void addShiftTime(ShiftTime shiftTime){
        this.shiftTimes.add(shiftTime.getShiftTimeID());
    }



}
