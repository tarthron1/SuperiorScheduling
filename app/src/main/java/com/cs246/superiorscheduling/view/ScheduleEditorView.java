package com.cs246.superiorscheduling.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.MainPresenter;

public class ScheduleEditorView extends AppCompatActivity implements MainPresenter.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_editor_view);
    }

    public void addShift(View view){
        // Open shift fragment
        ShiftFragment shiftFragment = new ShiftFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.schedule_container, shiftFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void addEmployee(View view){
        // Open employee fragment
        AddEmployeeFragment addEmployeeFragment = new AddEmployeeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.schedule_container, addEmployeeFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void addToSchedule(View view){
        // add shift with employees to schedule

    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyConfigChanged() {

    }
}