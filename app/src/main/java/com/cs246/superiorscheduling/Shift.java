package com.cs246.superiorscheduling;

import java.time.LocalDate;
import java.util.ArrayList;

public class Shift {

    String shiftType;
    LocalDate date;
    LocalDate beginTime;
    LocalDate endTime;
    ArrayList<ShiftTime> shiftTimes;
    int requiredEmployees;

    public Shift(LocalDate date, int requiredEmployees){

    }
}
