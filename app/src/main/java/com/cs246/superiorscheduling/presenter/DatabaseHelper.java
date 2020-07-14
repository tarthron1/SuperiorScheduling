package com.cs246.superiorscheduling.presenter;

import androidx.annotation.NonNull;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseHelper implements Listener{
    private FirebaseDatabase database;
    private String mAuthId;
    private User user;
    private User employee;
    private Company company;
    private ArrayList<User> employees;
    private ArrayList<Schedule> schedules;
    private ArrayList<Shift> shifts;
    private ArrayList<ShiftTime> shiftTimes;
    private ArrayList<Request> requestsByCompany;
    private ArrayList<Request> requestsByUser;
    private ArrayList<Listener> listeners;

    public DatabaseHelper(String mAuthId, FirebaseDatabase database, Listener listener){
        this.database = database;
        this.mAuthId = mAuthId;
        listeners = new ArrayList<>();
        listeners.add(listener);
        pullUser();
    }

    public User getUser(){
        return this.user;
    }

    public User getEmployee(){
        return this.employee;
    }

    public Company getCompany(){
        return this.company;
    }

    public ArrayList<User> getEmployees(){
        return this.employees;
    }

    public ArrayList<Schedule> getSchedules(){
        return this.schedules;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public ArrayList<Request> getRequestsByCompany() {
        return requestsByCompany;
    }

    public ArrayList<Request> getRequestsByUser() {
        return requestsByUser;
    }

    public ArrayList<ShiftTime> getShiftTimes() {
        return shiftTimes;
    }

    private void pullUser(){
        DatabaseReference databaseCurrentUser = database.getReference().child("users").child(mAuthId);
        databaseCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = null;
                user = snapshot.getValue(User.class);
                pullRequestsByUser();
                pullCompany();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pullEmployee(String userId){
        DatabaseReference databaseCurrentUser = database.getReference().child("users").child(userId);
        databaseCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employee = null;
                employee = snapshot.getValue(User.class);
                boolean containsEmployee = false;
                for (User user:employees
                     ) {
                    if (user.getUserID().equals(employee.getUserID())){
                        containsEmployee = true;
                    }
                }
                if (!containsEmployee)
                {
                    employees.add(employee);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pullCompany(){

        DatabaseReference databaseCompany = database.getReference().child("companies").child(user.getCompanies().get(0));
        databaseCompany.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                company = null;
                company = snapshot.getValue(Company.class);
                pullEmployeesByCompany();
                pullRequestsByCompany();
                pullSchedules();
                pullShifts();
                pullShiftTimes();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pullEmployeesByCompany(){
        employees = null;
        employees = new ArrayList<>();
        for (String employeeId: company.getActiveEmployeeList()
             ) {
            pullEmployee(employeeId);
        }
        for (String employeeId: company.getManagerList()
             ) {
            pullEmployee(employeeId);
        }
        for (String employeeId: company.getInactiveEmployeeList()
             ) {
            pullEmployee(employeeId);
        }

    }

    private void pullSchedules(){

        DatabaseReference databaseSchedules = database.getReference().child("schedule").child(company.getCompanyID());
        databaseSchedules.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schedules = null;
                schedules = new ArrayList<>();
                for (DataSnapshot schedule:snapshot.getChildren()
                     ) {
                    schedules.add(schedule.getValue(Schedule.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pullShifts(){
        DatabaseReference databaseShifts = database.getReference().child("shift").child(company.getCompanyID());
        databaseShifts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shifts = null;
                shifts = new ArrayList<>();
                for (DataSnapshot shift: snapshot.getChildren()
                     ) {
                    shifts.add(shift.getValue(Shift.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pullShiftTimes(){
        DatabaseReference databaseShiftTimes = database.getReference().child("shiftTime").child(company.getCompanyID());
        databaseShiftTimes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shiftTimes = null;
                shiftTimes = new ArrayList<>();
                for (DataSnapshot shiftTime:snapshot.getChildren()
                     ) {
                    shiftTimes.add(shiftTime.getValue(ShiftTime.class));
                }
                notifyDataReady();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pullRequestsByCompany(){
        for (String employee: company.getActiveEmployeeList()
             ) {
            DatabaseReference requestLocation = database.getReference().child("request").child(employee);
            requestLocation.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    requestsByCompany = null;
                    requestsByCompany = new ArrayList<>();
                    for (DataSnapshot request: snapshot.getChildren()
                         ) {
                        requestsByCompany.add(request.getValue(Request.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void pullRequestsByUser(){
        DatabaseReference requestLocation = database.getReference().child("request").child(user.getUserID());
        requestLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestsByUser = null;
                requestsByUser = new ArrayList<>();
                for (DataSnapshot request: snapshot.getChildren()
                     ) {
                    requestsByUser.add(request.getValue(Request.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUser(User user){
        DatabaseReference databaseCurrentUser = database.getReference().child("users").child(mAuthId);
        databaseCurrentUser.setValue(user);
    }

    public void setCompany(Company company) {
        DatabaseReference databaseCompany = database.getReference().child("companies").child(user.getCompanies().get(0));
        databaseCompany.setValue(company);
    }

    public void addSchedule(Schedule schedule){
        DatabaseReference databaseSchedules = database.getReference().child("schedule").child(company.getCompanyID());
        databaseSchedules.push().setValue(schedule);
    }

    public void setShifts(ArrayList<Shift> shifts){
        DatabaseReference databaseShifts = database.getReference().child("shift").child(company.getCompanyID());
        databaseShifts.removeValue();
        for (Shift shift: shifts
             ) {
            databaseShifts.push().setValue(shift);
        }
    }
    public void addShift(Shift shift){
        DatabaseReference databaseShifts = database.getReference().child("shift").child(company.getCompanyID());
        databaseShifts.push().setValue(shift);
    }

    public void addShiftTime(ShiftTime shiftTime){
        DatabaseReference databaseShiftTimes = database.getReference().child("shiftTime").child(company.getCompanyID());
        databaseShiftTimes.push().setValue(shiftTime);
    }

    public void addRequest(Request request){
        DatabaseReference requestLocation = database.getReference().child("request").child(user.getUserID());
        requestLocation.push().setValue(request);
    }

    public void addListener(Listener listener){
        this.listeners.add(listener);
    }


    @Override
    public void notifyDataReady() {
        for (Listener listener: listeners
             ) {
            listener.notifyDataReady();
        }
    }

    @Override
    public void notifyNewDataToSave() {

    }
}
