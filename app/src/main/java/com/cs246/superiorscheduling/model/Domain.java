package com.cs246.superiorscheduling.model;

import java.util.ArrayList;

public class Domain {

    private ArrayList<Company> companies = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    // Companies Getter
    public ArrayList<Company> getCompanies(){
        return companies;
    }

    // Users Getter
    public ArrayList<User> getUsers() {
        return users;
    }

    // Companies Setter
    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }

    // Users Setter
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    // Adds Company to the list
    public void addCompany(Company company){
        companies.add(company);
    }

    // Adds User to the list
    public void addUser(User user){
        users.add(user);
    }
}
