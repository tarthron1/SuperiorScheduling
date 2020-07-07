package com.cs246.superiorscheduling.model;

import android.widget.EditText;

import java.time.LocalDate;
import java.util.UUID;

public class Request {

    private String requestID;
    UUID uuid = UUID.randomUUID();
    String requesterID;
    LocalDate date;
    String shiftID = null;
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
    public String getRequesterID() {
        return requesterID;
    }

    // Requester Setter
    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
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
    public Request(User requester, LocalDate date, String shiftID, String reason){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.shiftID = shiftID;
        this.reason = reason;
        this.requestID = uuid.toString();
    }

    // Constructor (w/o reason) *changed order of parameters (06/27/2020)
    public Request(User requester, String shiftID, LocalDate date){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.shiftID = shiftID;
        this.requestID = uuid.toString();
    }

    // Constructor (w/o shift)
    public Request(User requester, LocalDate date, String reason){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.reason = reason;
        this.requestID = uuid.toString();
    }

    // Constructor (w/o reason & shift)
    public Request(User requester, LocalDate date){
        this.requesterID = requester.getUserID();
        this.date = date;
        this.requestID = uuid.toString();
    }
}
