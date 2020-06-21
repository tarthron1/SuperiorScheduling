package com.cs246.superiorscheduling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {

    LocalDate startDay;
    LocalDate endDay;
    public ArrayList<Shift> shiftList;
    boolean published = false;

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
    public ArrayList<Shift> getShiftList() {
        return shiftList;
    }

    // ShiftList Setter
    public void setShiftList(ArrayList<Shift> shiftList) {
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
    }

    // Creates shift
    public void createShift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime){
        if (!this.published){
        this.shiftList.add(new Shift(date, requiredEmployees, beginTime, endTime));
         }
    }

    // Creates shift (specific?)
    public void createShift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime, String shiftType){
        if (!this.published){
        this.shiftList.add(new Shift(date, requiredEmployees, beginTime, endTime, shiftType));
        }
    }

    // Publishes Schedule
    public void publishSchedule(){
        this.published = true;
    }

}
