package com.cs246.superiorscheduling.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.ScheduleViewPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

// The Schedule View
public class ScheduleViewActivity extends AppCompatActivity implements Listener {
    private ScheduleViewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        presenter = new ScheduleViewPresenter(mAuth.getUid(), database, this);
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}
