package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;

// The Employee's View
public class EmployeeView extends AppCompatActivity implements Listener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view);
    }

    public void viewSchedule(View view){
        Intent intent = new Intent(this, ScheduleViewActivity.class);
        startActivity(intent);
    }

    // Ability to request time off
    public void requestTimeOff(View view){
        Intent intent = new Intent(this, RequestTimeOffActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}