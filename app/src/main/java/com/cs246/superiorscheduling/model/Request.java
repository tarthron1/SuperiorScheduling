package com.cs246.superiorscheduling.model;

import java.time.LocalDate;
import java.util.UUID;

public class Request {

    private String requestID;
    UUID uuid = UUID.randomUUID();
    User requester;
    LocalDate date;
    String shift = null;
    String reason = null;

    // ID Getter
    public String getRequestID() {
        return requestID;
    }

    // ID Setter
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

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
    public String getShift() {
        return shift;
    }

    // Shift Setter
    public void setShift(String shift) {
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
    public Request(User requester, LocalDate date, String shift, String reason){
        this.requester = requester;
        this.date = date;
        this.shift = shift;
        this.reason = reason;
        this.requestID = uuid.toString();
    }

    // Constructor (w/o reason) *changed order of parameters (06/27/2020)
    public Request(User requester, String shift, LocalDate date){
        this.requester = requester;
        this.date = date;
        this.shift = shift;
        this.requestID = uuid.toString();
    }

    // Constructor (w/o shift)
    public Request(User requester, LocalDate date, String reason){
        this.requester = requester;
        this.date = date;
        this.reason = reason;
        this.requestID = uuid.toString();
    }

    // Constructor (w/o reason & shift)
    public Request(User requester, LocalDate date){
        this.requester = requester;
        this.date = date;
        this.requestID = uuid.toString();
    }
}
