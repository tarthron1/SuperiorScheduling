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
        for (Listener listener:listeners
             ) {
            listener.notifyDataReady();
        }

    }

    @Override
    public void notifyNewDataToSave() {

    }
}
