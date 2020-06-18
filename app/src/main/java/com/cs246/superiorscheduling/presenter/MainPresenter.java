package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.User;

import java.util.ArrayList;

public class MainPresenter {

    private ArrayList<Listener> registeredDataListeners = new ArrayList<>();
    private ArrayList<Company> companies = new ArrayList<>();
    private User currentUser;

    public void registerListeners(Listener listener){
        registeredDataListeners.add(listener);
    }

    public void addCompany(Company company){
        companies.add(company);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void notifyUsersChangeOnCloud(){
        for (Listener listener: this.registeredDataListeners
             ) {
            listener.notifyChangeOnCloud();
        }
    }

    public void notifyCloudNewDataToSave(){
        for (Listener listener: this.registeredDataListeners
             ) {
            listener.notifyNewDataToSave();
        }
    }

}
