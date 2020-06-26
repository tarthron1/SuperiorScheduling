package com.cs246.superiorscheduling.model;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class User {

    private String uid;
    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthDate;
    UUID uuid = UUID.randomUUID();
    private HashMap<String, Company> companies = new HashMap<>();

    public User(){

    }

    // Uid Setter
    public void setUid(String uid) {
        this.uid = uid;
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
    public HashMap<String, Company> getCompanies() {
        return companies;
    }

    // Companies Setter
    public void setCompanies(HashMap<String,Company> companies) {
        this.companies = companies;
    }

    // Constructor (Specified)
    public User(String uid, String firstName, String lastName, String nickName, Date birthDate){
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.firstName = uuid.toString();
        this.lastName = uuid.toString();
        this.nickName = uuid.toString();
    }

    // Constructor (Basic)
    public User(String firstName, String lastName, Date birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.firstName = uuid.toString();
        this.lastName = uuid.toString();
    }

    // Uid Getter
    public String getUid() {
        return uid;
    }

    public void addCompany(Company company){
        this.companies.put(company.getName(), company);
    }






}
