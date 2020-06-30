package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.EmployerViewPresenter;
import com.cs246.superiorscheduling.presenter.Listener;

// The Employer's View
public class EmployerView extends AppCompatActivity implements Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view);
        EmployerViewPresenter presenter = new EmployerViewPresenter();
    }

    // Ability to view Accounts
    public void viewAccounts(View view) {
        Intent intent = new Intent(this, ManageAccountsActivity.class);
        startActivity(intent);
    }

    // TODO: Create Ability to view the Current Schedule
    public void viewSchedule(View view){
        Intent intent = new Intent(this, ScheduleViewActivity.class);
        startActivity(intent);
    }

    // Ability to edit Schedules
    public void editSchedule(View view) {
        Intent intent = new Intent(this, EditScheduleActivity.class);
        startActivity(intent);
    }


    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}