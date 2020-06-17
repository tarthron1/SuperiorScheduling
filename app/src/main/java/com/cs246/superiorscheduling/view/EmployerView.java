package com.cs246.superiorscheduling.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.MainPresenter;
import com.cs246.superiorscheduling.view.AccountManagerView;

public class EmployerView extends AppCompatActivity implements MainPresenter.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view);
    }
    public void viewAccounts(View view) {
        Intent intent = new Intent(this, AccountManagerView.class);
        startActivity(intent);
    }

    public void viewSchedule(View view){
        //Open new activity with schedule fragment?
    }

    public void editSchedules(View view) {
        Intent intent = new Intent(this, ScheduleEditorView.class);
        startActivity(intent);
    }


    @Override
    public void notifyChangeOnCloud() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}