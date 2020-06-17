package com.cs246.superiorscheduling.model;

import java.util.Date;

public class User {

    private String uid;
    private String firstName;
    private String lastName;
    private String nickName;
    private Date birthDate;
    private boolean manager = false;
    private boolean active = false;

    public User(String uid, String firstName, String lastName, String nickName, Date birthDate, Boolean manager){
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
        if (manager){
            this.toggleManager();
            this.toggleActive();
        }
    }

    public User(String firstName, String lastName, Date birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public void toggleActive(){
        this.active = !this.active;
    }

    public void toggleManager(){
        this.manager = !this.manager;
    }

    public boolean isManager(){
        return manager;
    }
    public boolean isActive(){
        return active;
    }
}
