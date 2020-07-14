package com.cs246.superiorscheduling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Schedule {

    private String scheduleID;
    private Date startDay;
    private Date endDay;
    public ArrayList<String> shiftList;
    boolean published = false;

    public Schedule(){

    }

    // ID Getter
    public String getScheduleID() {
        return scheduleID;
    }

    // ID Setter
    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    // StartDay Getter
    public Date getStartDay() {
        return startDay;
    }

    // StartDay Setter
    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    // EndDay Getter
    public Date getEndDay() {
        return endDay;
    }

    // EndDay Setter
    public void setEndDay(Date endDay) {
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
    public Schedule(Date startDay, Date endDay){
        this.startDay = startDay;
        this.endDay = endDay;
        this.scheduleID = UUID.randomUUID().toString();
    }

    // Creates shift
    public void createShift(Shift shift){
        if (!this.published){
        this.shiftList.add(shift.getShiftID());
        }
    }

    // Publishes Schedule
    public void publishSchedule(){
        this.published = true;
    }

}
