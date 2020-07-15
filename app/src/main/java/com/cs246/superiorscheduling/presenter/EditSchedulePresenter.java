package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditSchedulePresenter implements Listener {
    private User currentUser;
    private Schedule currentSchedule;
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private ArrayList<Shift> shifts = new ArrayList<>();
    private ArrayList<ShiftTime> shiftTimes = new ArrayList<>();
    private Company currentCompany;
    private ArrayList<Listener> listeners = new ArrayList<>();
    private DatabaseHelper helper;

    public EditSchedulePresenter(String userID, FirebaseDatabase database, Listener listener) {
        listeners.add(listener);
        helper = new DatabaseHelper(userID, database, this);
    }

    @Override
    public void notifyDataReady() {
        currentUser = helper.getUser();
        schedules = helper.getSchedules();
        shifts = helper.getShifts();
        shiftTimes = helper.getShiftTimes();
        currentCompany = helper.getCompany();
        for (Listener listener : listeners
        ) {
            listener.notifyDataReady();
        }
    }

    @Override
    public void notifyNewDataToSave() {
        helper.addSchedule(currentSchedule);

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(ArrayList<Shift> shifts) {
        this.shifts = shifts;
    }

    public ArrayList<ShiftTime> getShiftTimes() {
        return shiftTimes;
    }

    public void setShiftTimes(ArrayList<ShiftTime> shiftTimes) {
        this.shiftTimes = shiftTimes;
    }

    public Company getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(Company currentCompany) {
        this.currentCompany = currentCompany;
    }

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void removeShift(Shift shift){

    }

    public void removeShiftTime(ShiftTime shiftTime){

    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }
}
