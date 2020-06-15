package com.cs246.superiorscheduling.model;

import java.time.LocalDate;

public class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String nickName;
    private LocalDate birthDate;
    private boolean manager;
    private boolean active = false;

    public User(String firstName, String lastName, String nickName, LocalDate birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birthDate = birthDate;
    }

    public User(String firstName, String lastName, LocalDate birthDate){
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
