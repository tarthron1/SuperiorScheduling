package com.cs246.superiorscheduling.presenter;

import androidx.annotation.NonNull;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPresenter implements Listener{

    private ArrayList<Listener> registeredDataListeners = new ArrayList<>();
    private ArrayList<Company> companies = new ArrayList<>();
    private User currentUser;
    private DatabaseHelper helper;
    private ArrayList<User> allUsers;

    public MainPresenter(FirebaseDatabase database, Listener listener){
        registeredDataListeners.add(listener);
        helper = new DatabaseHelper(database, this);
    }

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
        notifyNewDataToSave();
    }

    // CurrentUser Getter
    public User getCurrentUser(){
        return this.currentUser;
    }

    // Companies Getter
    public ArrayList<Company> getCompanies() {
        return companies;
    }

    //Get company by name
    public Company getCompanyByName(String companyName){
        for (Company company: this.getCompanies()
             ) {
            if(company.getName().equals(companyName)){
                return company;
            }
        }
        return null;
    }

    @Override
    public void notifyDataReady() {
        this.companies = helper.getCompanies();
        this.allUsers = helper.getAllUsers();
        for (Listener listener: registeredDataListeners
             ) {
            listener.notifyDataReady();
        }
    }

    @Override
    public void notifyNewDataToSave() {
        helper.setUser(this.currentUser);
        helper.setCompanies(this.companies);
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void updateMAuth(String mAuth){
        helper.setmAuth(mAuth);
    }


    public void setAllUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }
}
