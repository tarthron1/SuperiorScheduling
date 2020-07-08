package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;

import java.util.ArrayList;

public class EditSchedulePresenter {
    private User currentUser;
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private ArrayList<Shift> shifts = new ArrayList<>();
    private ArrayList<ShiftTime> shiftTimes = new ArrayList<>();
    private Company currentCompany;

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
}
