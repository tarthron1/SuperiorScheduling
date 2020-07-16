package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ScheduleViewPresenter implements Listener {

    private DatabaseHelper helper;
    private ArrayList<Schedule> schedules;
    private ArrayList<Shift> shifts;
    private ArrayList<ShiftTime> shiftTimes;
    private ArrayList<Listener> listeners;
    private User user;
    private Company company;
    private Schedule selectedSchedule;
    private ArrayList<User> employees;

    public ScheduleViewPresenter(String userID, FirebaseDatabase database, Listener listener){
        listeners = new ArrayList<>();
        listeners.add(listener);
        helper = new DatabaseHelper(userID, database, this);
    }
    @Override
    public void notifyDataReady() {
        user = helper.getUser();
        company = helper.getCompany();
        schedules = helper.getSchedules();
        shifts = helper.getShifts();
        shiftTimes = helper.getShiftTimes();
        employees = helper.getEmployees();
        for (Listener listener:listeners
             ) {
            listener.notifyDataReady();
        }
    }

    @Override
    public void notifyNewDataToSave() {

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

    public Schedule getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(Schedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    public ArrayList<User> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<User> employees) {
        this.employees = employees;
    }
}
