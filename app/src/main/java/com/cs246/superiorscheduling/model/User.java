package com.cs246.superiorscheduling.model;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private String uid;
    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthDate;
    private ArrayList<Company> companies;

    public User(String uid, String firstName, String lastName, String nickName, Date birthDate){
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
    }

    public User(String firstName, String lastName, Date birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getUid() {
        return uid;
    }
}
