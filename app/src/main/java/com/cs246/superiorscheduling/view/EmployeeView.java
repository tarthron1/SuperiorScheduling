package com.cs246.superiorscheduling.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.MainPresenter;
import com.cs246.superiorscheduling.view.AccountManagerView;

public class EmployeeView extends AppCompatActivity implements MainPresenter.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view);
    }

    public void viewSchedule(View view){
        ScheduleView scheduleView = new ScheduleView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.employee_container, scheduleView);
        fragmentTransaction.commit();
    }

    public void requestTimeoff(View view){
        TimeoffFragment timeoffFragment = new TimeoffFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.employee_container, timeoffFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyConfigChanged() {

    }
}