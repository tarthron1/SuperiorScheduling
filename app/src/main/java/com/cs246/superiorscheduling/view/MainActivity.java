package com.cs246.superiorscheduling.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs246.superiorscheduling.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        //if manager is true deliver EmployerView

        //if manager is false deliver EmployeeView
    }

    public void createAccount(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}