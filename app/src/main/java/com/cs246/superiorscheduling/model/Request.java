package com.cs246.superiorscheduling.model;

import java.time.LocalDate;

public class Request {

    User requester;
    LocalDate date;
    Shift shift = null;
    String reason = null;

    public Request(User requester, LocalDate date, Shift shift, String reason){
        this.requester = requester;
        this.date = date;
        this.shift = shift;
        this.reason = reason;
    }

    public Request(User requester, LocalDate date, Shift shift){
        this.requester = requester;
        this.date = date;
        this.shift = shift;
    }

    public Request(User requester, LocalDate date, String reason){
        this.requester = requester;
        this.date = date;
        this.reason = reason;
    }

    public Request(User requester, LocalDate date){
        this.requester = requester;
        this.date = date;
    }
}
