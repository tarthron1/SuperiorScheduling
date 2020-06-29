package com.cs246.superiorscheduling.model;

import java.util.ArrayList;
import java.util.UUID;

public class Company  {

    private String name;
    private String companyID;
    UUID uuid = UUID.randomUUID();
    ArrayList<String> inactiveEmployeeList = new ArrayList<>();
    ArrayList<String> activeEmployeeList = new ArrayList<>();
    ArrayList<String> managerList = new ArrayList<>();
    ArrayList<String> scheduleList;
    ArrayList<String> requestList;

    // Default?
    public Company(){

    }

    // Inactive Employee list Getter
    public ArrayList<String> getInactiveEmployeeList() {
        return inactiveEmployeeList;
    }

    // Inactive Employee list Setter
    public void setInactiveEmployeeList(ArrayList<String> inactiveEmployeeList) {
        this.inactiveEmployeeList = inactiveEmployeeList;
    }

    // Active Employee list Setter
    public void setActiveEmployeeList(ArrayList<String> activeEmployeeList) {
        this.activeEmployeeList = activeEmployeeList;
    }

    // Manager list Setter
    public void setManagerList(ArrayList<String> managerList) {
        this.managerList = managerList;
    }

    // Schedule list Getter
    public ArrayList<String> getScheduleList() {
        return scheduleList;
    }

    // Schedule list Setter
    public void setScheduleList(ArrayList<String> scheduleList) {
        this.scheduleList = scheduleList;
    }

    // Request list Getter
    public ArrayList<String> getRequestList() {
        return requestList;
    }

    // Request list Setter
    public void setRequestList(ArrayList<String> requestList) {
        this.requestList = requestList;
    }

    // Constructor
    public Company(String name, User manager){
        this.setName(name);
        this.addManager(manager);
        this.companyID = uuid.toString();

    }

    public String getCompanyID() {
        return companyID;
    }

    // Manager list Getter
    public ArrayList<String> getManagerList() {
        return managerList;
    }

    // Active Employee list Getter
    public ArrayList<String> getActiveEmployeeList(){
        return activeEmployeeList;
    }

    // Adds employee to the list (inactive by default)
    public void addEmployee(User user){
        this.inactiveEmployeeList.add(user.getUserID());
    }

    // Makes employee active
    public void makeActive(User user){
        if (inactiveEmployeeList.contains(user.getUserID()) && (!activeEmployeeList.contains(user.getUserID()))){
            inactiveEmployeeList.remove(user.getUserID());
            activeEmployeeList.add(user.getUserID());
        } else if (inactiveEmployeeList.contains(user.getUserID()) && (activeEmployeeList.contains(user.getUserID()))){
            inactiveEmployeeList.remove(user.getUserID());
        } else if ((!inactiveEmployeeList.contains(user.getUserID())) && activeEmployeeList.contains(user.getUserID())){

        } else {

        }
    }

    // Makes employee inactive
    public void makeInactive(User user){
        if (activeEmployeeList.contains(user.getUserID())){
            activeEmployeeList.remove(user.getUserID());
            if (!inactiveEmployeeList.contains(user.getUserID())){
                inactiveEmployeeList.add(user.getUserID());
            }
        }
    }

    // Adds manager to the list
    public void addManager(User user){
        managerList.add(user.getUserID());
    }

    // Removes manager from the list
    public void removeManager(User user){
        if (managerList.size() >= 1){
            managerList.remove(user.getUserID());
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
        this.scheduleList.add(schedule.getScheduleID());
    }

    // Adds request to the list
    public void addRequest(Request request){
        this.requestList.add(request.getRequestID());
    }
}
