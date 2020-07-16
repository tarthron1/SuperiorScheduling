package com.cs246.superiorscheduling.model;

import java.util.ArrayList;
import java.util.UUID;

public class Company  {

    private String name;
    private String companyID;
    UUID uuid = UUID.randomUUID();
    ArrayList<String> inactiveEmployeeList;
    ArrayList<String> activeEmployeeList;
    ArrayList<String> managerList;
    ArrayList<String> scheduleList;
    ArrayList<String> requestList;

    // Default?
    public Company(){

    }

    // Constructor
    public Company(String name, User manager){
        inactiveEmployeeList = new ArrayList<>();
        activeEmployeeList = new ArrayList<>();
        managerList = new ArrayList<>();
        scheduleList = new ArrayList<>();
        requestList = new ArrayList<>();

        this.setName(name);
        this.addManager(manager);
        this.companyID = uuid.toString();

    }
    // Sets Name
    public void setName(String name) {
        this.name = name;
    }

    // Gets Name
    public String getName(){
        return this.name;
    }

    // Inactive Employee list Getter
    public ArrayList<String> getInactiveEmployeeList() {
        return inactiveEmployeeList;
    }

    // Inactive Employee list Setter
    public void setInactiveEmployeeList(ArrayList<String> inactiveEmployeeList) {
        this.inactiveEmployeeList = inactiveEmployeeList;
    }
    // Active Employee list Getter
    public ArrayList<String> getActiveEmployeeList(){
        return activeEmployeeList;
    }
    // Active Employee list Setter
    public void setActiveEmployeeList(ArrayList<String> activeEmployeeList) {
        this.activeEmployeeList = activeEmployeeList;
    }
    // Manager list Getter
    public ArrayList<String> getManagerList() {
        return managerList;
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


    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    // Adds employee to the list (inactive by default)
    public void addEmployee(User user){
        //TODO change back to inactive list.
        this.activeEmployeeList.add(user.getUserID());
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


    // Adds schedule to the list
    public void addSchedule(Schedule schedule){
        this.scheduleList.add(schedule.getScheduleID());
    }

    // Adds request to the list
    public void addRequest(Request request){
        this.requestList.add(request.getRequestID());
    }

    public void toggleActiveEmployee(User employee) {
        if (activeEmployeeList.contains(employee.getUserID())) {
            activeEmployeeList.remove(employee.getUserID());
            inactiveEmployeeList.add(employee.getUserID());
        }
        else {
            activeEmployeeList.add(employee.getUserID());
            inactiveEmployeeList.remove(employee.getUserID());
        }
    }
}
