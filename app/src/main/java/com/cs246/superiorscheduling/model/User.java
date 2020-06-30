package com.cs246.superiorscheduling.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private String userID;
    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthDate;
    private ArrayList<String> companies = new ArrayList<>();

    //  Default?
    public User(){

    }

    // Constructor (Basic)
    public User(String firstName, String lastName, Date birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    // Constructor (Specified)
    public User(String userID, String firstName, String lastName, String nickName, Date birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.userID = userID;
    }

    // Uid Getter
    public String getUserID() {
        return userID;
    }

    // Uid Setter
    public void setUserID(String userID) {
        this.userID = userID;
    }

    // FirstName Getter
    public String getFirstName() {
        return firstName;
    }

    // FirstName Setter
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // LastName Getter
    public String getLastName() {
        return lastName;
    }

    // LastName Setter
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // NickName Getter
    public String getNickName() {
        return nickName;
    }

    // NickName Setter
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    // BirthDate Getter
    public Date getBirthDate() {
        return birthDate;
    }

    // BirthDate Setter
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    // Companies Getter
    public ArrayList<String> getCompanies() {
        return companies;
    }

    // Companies Setter
    public void setCompanies(ArrayList<String> companies) {
        this.companies = companies;
    }


    public void addCompany(Company company){
        this.companies.add(company.getCompanyID());
    }

}
