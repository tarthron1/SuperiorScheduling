package com.cs246.superiorscheduling.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;

public class RequestTimeOffActivity extends AppCompatActivity implements Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_off);
    }

    public void submitRequest(View view) {

    }

    @Override
    public void notifyChangeOnCloud() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}
