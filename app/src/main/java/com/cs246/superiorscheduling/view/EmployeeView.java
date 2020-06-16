package com.cs246.superiorscheduling.view;

import androidx.appcompat.app.AppCompatActivity;

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

    public void viewSchedule(View view) {
        Intent intent = new Intent(this, AccountManagerView.class);
        startActivity(intent);
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyConfigChanged() {

    }
}