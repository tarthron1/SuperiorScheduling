package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// The view used to edit the Schedules
public class EditScheduleActivity extends AppCompatActivity implements Listener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseUser, databaseSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
    }

    // Ability to add shifts
    public void addShift(View view){
        Intent intent = new Intent(this, AddShiftActivity.class);
        startActivity(intent);
    }

    public void clearSchedule(View view) {

    }

    public void publishSchedule(View view) {

    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}