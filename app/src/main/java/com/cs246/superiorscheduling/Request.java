package com.cs246.superiorscheduling;

import java.time.LocalDate;

public class Request {

    Employee requestor;
    LocalDate date;
    Shift shift = null;
    String reason = null;

    public Request(Employee requestor, LocalDate date, Shift shift, String reason){

    }

    public Request(Employee requestor, LocalDate date, Shift shift){

    }

    public Request(Employee requestor, LocalDate date, String reason){

    }

    public Request(Employee requestor, LocalDate date){
        
    }
}
