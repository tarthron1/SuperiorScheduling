package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void addAccount(View view){
        /*
        // get switch result from create account form
        Switch managerSwitch = findViewById(R.id.manager_switch);
        //if manager switch is checked deliver EmployerView
        if(managerSwitch.isChecked()) {
            Intent employer = new Intent(this, EmployerView.class);
            startActivity(employer);
        }
        //if manager switch is not checked deliver EmployeeView
        else if(!managerSwitch.isChecked()) {
            Intent employee = new Intent(this, EmployeeView.class);
            startActivity(employee);
        } */
    }
}
