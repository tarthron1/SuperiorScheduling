package com.cs246.superiorscheduling.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.MainPresenter;

public class SchedulerEditorView extends AppCompatActivity implements MainPresenter.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_editor_view);
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyConfigChanged() {

    }
}