package com.cs246.superiorscheduling.presenter;

import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;

import java.util.ArrayList;

public class AddEmployeePresenter {
    private User currentUser;
    private Company currentCompany;
    private ArrayList<Request> requests;
    private ArrayList<User> employeeList;
    private Shift shift;
    private ShiftTime shiftTime;

    public AddEmployeePresenter() {

    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public Company getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(Company currentCompany) {
        this.currentCompany = currentCompany;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void addEmployee(User employee){
        employeeList.add(employee);
    }

    public ArrayList<User> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<User> employeeList) {
        this.employeeList = employeeList;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public ShiftTime getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(ShiftTime shiftTime) {
        this.shiftTime = shiftTime;
    }
}
