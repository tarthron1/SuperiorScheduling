package com.cs246.superiorscheduling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class Schedule {

    private String scheduleID;
    UUID uuid = UUID.randomUUID();
    LocalDate startDay;
    LocalDate endDay;
    public ArrayList<String> shiftList;
    boolean published = false;

    // ID Getter
    public String getScheduleID() {
        return scheduleID;
    }

    // ID Setter
    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    // StartDay Getter
    public LocalDate getStartDay() {
        return startDay;
    }

    // StartDay Setter
    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    // EndDay Getter
    public LocalDate getEndDay() {
        return endDay;
    }

    // EndDay Setter
    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    // ShiftList Getter
    public ArrayList<String> getShiftList() {
        return shiftList;
    }

    // ShiftList Setter
    public void setShiftList(ArrayList<String> shiftList) {
        this.shiftList = shiftList;
    }

    // isPublished Getter
    public boolean isPublished() {
        return published;
    }

    // isPublished Setter
    public void setPublished(boolean published) {
        this.published = published;
    }

    // Constructor
    public Schedule(LocalDate startDay, LocalDate endDay){
        this.startDay = startDay;
        this.endDay = endDay;
        this.scheduleID = uuid.toString();
    }

    // Creates shift
    public void createShift(Schedule parentSchedule, LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime){
        if (!this.published){
        this.shiftList.add(new Shift(parentSchedule, date, requiredEmployees, beginTime, endTime).toString());
         }
    }

    // Creates shift (specific?)
    public void createShift(Schedule parentSchedule, LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime, String shiftType){
        if (!this.published){
        this.shiftList.add(new Shift(parentSchedule, date, requiredEmployees, beginTime, endTime, shiftType).toString());
        }
    }

    // Publishes Schedule
    public void publishSchedule(){
        this.published = true;
    }

}
