package com.cs246.superiorscheduling;

import java.time.LocalDate;

public class Request {

    User requestor;
    LocalDate date;
    Shift shift = null;
    String reason = null;

    public Request(User requestor, LocalDate date, Shift shift, String reason){

    }

    public Request(User requestor, LocalDate date, Shift shift){

    }

    public Request(User requestor, LocalDate date, String reason){

    }

    public Request(User requestor, LocalDate date){

    }
}
