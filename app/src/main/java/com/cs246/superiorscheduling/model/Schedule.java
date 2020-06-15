package com.cs246.superiorscheduling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {

    LocalDate startDay;
    LocalDate endDay;
    public ArrayList<Shift> shiftList;
    boolean published = false;

    public Schedule(LocalDate startDay, LocalDate endDay){
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public void createShift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime){
        if (!this.published){
        this.shiftList.add(new Shift(date, requiredEmployees, beginTime, endTime));
         }
    }

    public void createShift(LocalDate date, int requiredEmployees, LocalTime beginTime, LocalTime endTime, String shiftType){
        if (!this.published){
        this.shiftList.add(new Shift(date, requiredEmployees, beginTime, endTime, shiftType));
        }
    }

    public void publishSchedule(){
        this.published = true;
    }

}
