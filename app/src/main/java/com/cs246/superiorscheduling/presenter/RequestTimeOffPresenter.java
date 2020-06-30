package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.User;

import java.util.ArrayList;

public class RequestTimeOffPresenter {
    private User currentUser;
    private ArrayList<Schedule> scheduleList;
    private ArrayList<String> shiftIdList;
    private ArrayList<Shift> shiftList;
    private ArrayList<Request> userRequests;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<String> getShiftIdList() {
        return shiftIdList;
    }

    public void setShiftIdList(ArrayList<String> shiftIdList) {
        this.shiftIdList = shiftIdList;
    }

    public ArrayList<Request> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(ArrayList<Request> userRequests) {
        this.userRequests = userRequests;
    }

    public ArrayList<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(ArrayList<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    public ArrayList<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ArrayList<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void addRequest(Request request){
        this.userRequests.add(request);
    }
}
