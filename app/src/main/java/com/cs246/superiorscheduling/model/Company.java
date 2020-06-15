package com.cs246.superiorscheduling.model;

import java.util.ArrayList;

public class Company {

    ArrayList<User> userList;
    ArrayList<Schedule> scheduleList;
    ArrayList<Request> requestList;

    public void addUser(User user){
        this.userList.add(user);
    }

    public void addSchedule(Schedule schedule){
        this.scheduleList.add(schedule);
    }

    public void addRequest(Request request){
        this.requestList.add(request);
    }
}
