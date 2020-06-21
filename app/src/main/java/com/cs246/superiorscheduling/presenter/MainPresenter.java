package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.User;

import java.util.ArrayList;

public class MainPresenter {

    private ArrayList<Listener> registeredDataListeners = new ArrayList<>();
    private ArrayList<Company> companies = new ArrayList<>();
    private User currentUser;

    // Registers listener
    public void registerListeners(Listener listener){
        registeredDataListeners.add(listener);
    }

    // Adds company
    public void addCompany(Company company){
        companies.add(company);
    }

    // Sets current user and notifies the cloud
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        notifyCloudNewDataToSave();
    }

    // CurrentUser Getter
    public User getCurrentUser(){
        return this.currentUser;
    }

    // Companies Getter
    public ArrayList<Company> getCompanies() {
        return companies;
    }

    // Notifies change on Cloud?
    public void notifyUsersChangeOnCloud(){
        for (Listener listener: this.registeredDataListeners
             ) {
            listener.notifyChangeOnCloud();
        }
    }

    // Notifies the Cloud to save NewData
    public void notifyCloudNewDataToSave(){
        for (Listener listener: this.registeredDataListeners
             ) {
            listener.notifyNewDataToSave();
        }
    }

}
