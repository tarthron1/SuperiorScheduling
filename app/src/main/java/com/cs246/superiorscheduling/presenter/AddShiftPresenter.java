package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddShiftPresenter implements Listener {
    private DatabaseHelper helper;
    private ArrayList<Listener> listeners;
    private User user;
    private Company company;
    private ArrayList<Shift> shifts;
    private Shift shift;


    public AddShiftPresenter(String userId, FirebaseDatabase database, Listener listener){
        listeners = new ArrayList<>();
        listeners.add(listener);
        helper = new DatabaseHelper(userId, database, this);
    }

    @Override
    public void notifyDataReady() {
        user = helper.getUser();
        company = helper.getCompany();
        shifts = helper.getShifts();
        for (Listener listener: listeners
             ) {
            listener.notifyDataReady();
        }

    }

    @Override
    public void notifyNewDataToSave() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<Shift> shifts) {
        this.shifts = shifts;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
