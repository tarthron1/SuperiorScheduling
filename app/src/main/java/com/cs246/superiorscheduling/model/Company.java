package com.cs246.superiorscheduling.model;

import android.os.Parcelable;

import java.util.ArrayList;

public class Company  {

    private String name;
    ArrayList<User> inactiveEmployeeList = new ArrayList<>();
    ArrayList<User> activeEmployeeList = new ArrayList<>();
    ArrayList<User> managerList = new ArrayList<>();
    ArrayList<Schedule> scheduleList;
    ArrayList<Request> requestList;

    public Company(String name, User manager){
        this.name = name;
        this.addManager(manager);
    }

    public ArrayList<User> getManagerList() {
        return managerList;
    }

    public ArrayList<User> getActiveEmployeeList(){
        return activeEmployeeList;
    }

    public void addEmployee(User user){
        this.inactiveEmployeeList.add(user);
    }

    public void makeActive(User user){
        if (inactiveEmployeeList.contains(user) && (!activeEmployeeList.contains(user))){
            inactiveEmployeeList.remove(user);
            activeEmployeeList.add(user);
        } else if (inactiveEmployeeList.contains(user) && (activeEmployeeList.contains(user))){
            inactiveEmployeeList.remove(user);
        } else if ((!inactiveEmployeeList.contains(user)) && activeEmployeeList.contains(user)){

        } else {

        }
    }

    public void makeInactive(User user){
        if (activeEmployeeList.contains(user)){
            activeEmployeeList.remove(user);
            if (!inactiveEmployeeList.contains(user)){
                inactiveEmployeeList.add(user);
            }
        }
    }

    public void addManager(User user){
        managerList.add(user);
    }

    public void removeManager(User user){
        if (managerList != null){
            managerList.remove(user);
        }
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void addSchedule(Schedule schedule){
        this.scheduleList.add(schedule);
    }

    public void addRequest(Request request){
        this.requestList.add(request);
    }
}
