package com.cs246.superiorscheduling.model;

import java.util.Date;
import java.util.UUID;

public class Request {

    private String requestID;
    String requesterID;
    Date date;
    String shiftID = null;
    String reason = null;

    public Request(){

    }

    // ID Getter
    public String getRequestID() {
        return requestID;
    }

    // ID Setter
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    // Requester Getter
    public String getRequesterID() {
        return requesterID;
    }

    // Requester Setter
    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
    }

    // Date Getter
    public Date getDate() {
        return date;
    }

    // Date Setter
    public void setDate(Date date) {
        this.date = date;
    }

    // Shift Getter
    public String getShiftID() {
        return shiftID;
    }

    // Shift Setter
    public void setShift(String shiftID) { this.shiftID = shiftID; }

    // Reason Getter
    public String getReason() {
        return reason;
    }

    // Reason Setter
    public void setReason(String reason) {
        this.reason = reason;
    }

    // Constructor (full)
    public Request(User requester, Date date, String shiftID, String reason){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.shiftID = shiftID;
        this.reason = reason;
        this.requestID = UUID.randomUUID().toString();
    }

    // Constructor (w/o reason) *changed order of parameters (06/27/2020)
    public Request(User requester, String shiftID, Date date){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.shiftID = shiftID;
        this.requestID = UUID.randomUUID().toString();
    }

    // Constructor (w/o shift)
    public Request(User requester, Date date, String reason){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.reason = reason;
        this.requestID = UUID.randomUUID().toString();
    }

    // Constructor (w/o reason & shift)
    public Request(User requester, Date date){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.requestID = UUID.randomUUID().toString();
    }
}
