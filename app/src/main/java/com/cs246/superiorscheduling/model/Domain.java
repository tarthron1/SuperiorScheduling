package com.cs246.superiorscheduling.model;

import java.util.ArrayList;
// TODO: Is Class Necessary?
public class Domain {

    private ArrayList<String> companies = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();

    // Companies Getter
    public ArrayList<String> getCompanies(){
        return companies;
    }

    // Users Getter
    public ArrayList<String> getUsers() {
        return users;
    }

    // Companies Setter
    public void setCompanies(ArrayList<String> companies) {
        this.companies = companies;
    }

    // Users Setter
    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    // Adds Company to the list
    public void addCompany(String company){
        companies.add(company);
    }

    // Adds User to the list
    public void addUser(String user){
        users.add(user);
    }
}
