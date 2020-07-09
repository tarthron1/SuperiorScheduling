package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ManageAccountsPresenter implements Listener {
    private Company currentCompany;
    private User currentUser;
    private ArrayList<User> employeeList = new ArrayList<>();
    private ArrayList<Listener> listeners;
    private DatabaseHelper helper;

    public ManageAccountsPresenter(String userID, FirebaseDatabase database, Listener listener){
        listeners = new ArrayList<>();
        listeners.add(listener);
        helper = new DatabaseHelper(userID, database, this);

    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentCompany(Company currentCompany) {
        this.currentCompany = currentCompany;
    }

    public Company getCurrentCompany() {
        return currentCompany;
    }

    public ArrayList<User> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<User> employeeList) {
        this.employeeList = employeeList;
    }

    public void addEmployee(User user){
        if (!getEmployeeList().contains(user)){
            employeeList.add(user);
        }
    }

    public void removeEmployee(User user){
        if (getEmployeeList().contains(user)){
            employeeList.remove(user);
        }
    }

    @Override
    public void notifyDataReady() {
        currentUser = helper.getUser();
        currentCompany = helper.getCompany();
        employeeList = helper.getEmployees();
        for (Listener listener: listeners
             ) {
            listener.notifyDataReady();
        }

    }

    @Override
    public void notifyNewDataToSave() {
        helper.setUser(currentUser);
        helper.setCompany(currentCompany);
    }
}
