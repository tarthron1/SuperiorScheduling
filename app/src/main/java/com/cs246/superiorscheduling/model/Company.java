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

    public Company(){

    }

    // Inactive Employee list Getter
    public ArrayList<User> getInactiveEmployeeList() {
        return inactiveEmployeeList;
    }

    // Inactive Employee list Setter
    public void setInactiveEmployeeList(ArrayList<User> inactiveEmployeeList) {
        this.inactiveEmployeeList = inactiveEmployeeList;
    }

    // Active Employee list Setter
    public void setActiveEmployeeList(ArrayList<User> activeEmployeeList) {
        this.activeEmployeeList = activeEmployeeList;
    }

    // Manager list Setter
    public void setManagerList(ArrayList<User> managerList) {
        this.managerList = managerList;
    }

    // Schedule list Getter
    public ArrayList<Schedule> getScheduleList() {
        return scheduleList;
    }

    // Schedule list Setter
    public void setScheduleList(ArrayList<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    // Request list Getter
    public ArrayList<Request> getRequestList() {
        return requestList;
    }

    // Request list Setter
    public void setRequestList(ArrayList<Request> requestList) {
        this.requestList = requestList;
    }

    // Constructor
    public Company(String name, User manager){
        this.setName(name);
        this.addManager(manager);
    }

    // Manager list Getter
    public ArrayList<User> getManagerList() {
        return managerList;
    }

    // Active Employee list Getter
    public ArrayList<User> getActiveEmployeeList(){
        return activeEmployeeList;
    }

    // Adds employee to the list (inactive by default)
    public void addEmployee(User user){
        this.inactiveEmployeeList.add(user);
    }

    // Makes employee active
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

    // Makes employee inactive
    public void makeInactive(User user){
        if (activeEmployeeList.contains(user)){
            activeEmployeeList.remove(user);
            if (!inactiveEmployeeList.contains(user)){
                inactiveEmployeeList.add(user);
            }
        }
    }

    // Adds manager to the list
    public void addManager(User user){
        managerList.add(user);
    }

    // Removes manager from the list
    public void removeManager(User user){
        if (managerList.size() >= 1){
            managerList.remove(user);
        }
    }

    // Sets Name
    public void setName(String name) {
        this.name = name;
    }

    // Gets Name
    public String getName(){
        return this.name;
    }

    // Adds schedule to the list
    public void addSchedule(Schedule schedule){
        this.scheduleList.add(schedule);
    }

    // Adds request to the list
    public void addRequest(Request request){
        this.requestList.add(request);
    }
}
