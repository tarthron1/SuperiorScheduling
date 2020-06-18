package com.cs246.superiorscheduling.model;

import java.util.ArrayList;

public class Domain {

    private ArrayList<Company> companies = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    public ArrayList<Company> getCompanies(){
        return companies;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addCompany(Company company){
        companies.add(company);
    }

    public void addUser(User user){
        users.add(user);
    }
}
