package com.cs246.superiorscheduling.presenter;

import android.provider.ContactsContract;

import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestTimeOffPresenter implements Listener{
    private User currentUser;
    private ArrayList<Schedule> scheduleList= new ArrayList<>();
    private ArrayList<String> shiftIdList= new ArrayList<>();
    private ArrayList<Shift> shiftList= new ArrayList<>();
    private ArrayList<Request> userRequests = new ArrayList<>();
    private HashMap<String, Shift> shiftTypeList = new HashMap<>();
    private Request newRequest;
    private DatabaseHelper helper;
    private ArrayList<Listener> listeners;

    public RequestTimeOffPresenter(String userId, FirebaseDatabase database, Listener listener){
        listeners = new ArrayList<>();
        listeners.add(listener);
        helper = new DatabaseHelper(userId, database, this);
    }

    @Override
    public void notifyDataReady() {
        currentUser = helper.getUser();
        scheduleList = helper.getSchedules();
        shiftList = helper.getShifts();
        userRequests = helper.getRequestsByUser();
        for (Shift shift: shiftList
             ) {
            shiftIdList.add(shift.getShiftID());
            addShiftType(shift, shift.getShiftType());
        }
        for (Listener listener: listeners
             ) {
            listener.notifyDataReady();
        }
    }

    @Override
    public void notifyNewDataToSave() {
        helper.setUser(currentUser);
        helper.addRequest(newRequest);

    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<String> getShiftIdList() {
        return shiftIdList;
    }

    public void setShiftIdList(ArrayList<String> shiftIdList) {
        this.shiftIdList = shiftIdList;
    }

    public ArrayList<Request> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(ArrayList<Request> userRequests) {
        this.userRequests = userRequests;
    }

    public ArrayList<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(ArrayList<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    public ArrayList<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ArrayList<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void addRequest(Request request){
        this.userRequests.add(request);
    }

    public HashMap<String, Shift> getShiftTypeList() {
        return shiftTypeList;
    }

    public void setShiftTypeList(HashMap<String, Shift> shiftTypeList) {
        this.shiftTypeList = shiftTypeList;
    }

    public void addShiftType(Shift shift, String shiftType){
        shiftTypeList.put(shiftType, shift);
    }

    public Request getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(Request newRequest) {
        this.newRequest = newRequest;
    }


}
