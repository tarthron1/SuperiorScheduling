package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.User;

import java.util.ArrayList;

public class ManageAccountsPresenter {
    private Company currentCompany;
    private User currentUser;
    private ArrayList<User> employeeList = new ArrayList<>();

    public ManageAccountsPresenter(){

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
}
