package com.cs246.superiorscheduling;

import java.time.LocalDate;

public class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String nickName;
    private LocalDate birthdate;
    private boolean manager;
    private boolean active;

    public User(String firstName, String lastName, String nickName, LocalDate birthdate){

    }

    public User(String firstName, String lastName, LocalDate birthdate){

    }

    public void toggleActive(){

    }

    public boolean isActive(){
        return active;
    }
}
