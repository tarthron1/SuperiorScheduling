package com.cs246.superiorscheduling.model;

import java.time.LocalDate;

public class Request {

    User requester;
    LocalDate date;
    Shift shift = null;
    String reason = null;

    // Requester Getter
    public User getRequester() {
        return requester;
    }

    // Requester Setter
    public void setRequester(User requester) {
        this.requester = requester;
    }

    // Date Getter
    public LocalDate getDate() {
        return date;
    }

    // Date Setter
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Shift Getter
    public Shift getShift() {
        return shift;
    }

    // Shift Setter
    public void setShift(Shift shift) {
        this.shift = shift;
    }

    // Reason Getter
    public String getReason() {
        return reason;
    }

    // Reason Setter
    public void setReason(String reason) {
        this.reason = reason;
    }

    // Constructor (full)
    public Request(User requester, LocalDate date, Shift shift, String reason){
        this.requester = requester;
        this.date = date;
        this.shift = shift;
        this.reason = reason;
    }

    // Constructor (w/o reason)
    public Request(User requester, LocalDate date, Shift shift){
        this.requester = requester;
        this.date = date;
        this.shift = shift;
    }

    // Constructor (w/o shift)
    public Request(User requester, LocalDate date, String reason){
        this.requester = requester;
        this.date = date;
        this.reason = reason;
    }

    // Constructor (w/o reason & shift)
    public Request(User requester, LocalDate date){
        this.requester = requester;
        this.date = date;
    }
}
