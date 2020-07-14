package com.cs246.superiorscheduling.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.ScheduleViewPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void openDatePickerDialog(View view) {
        ScheduleViewDialog dialog = new ScheduleViewDialog();
        FragmentManager fragmentManager = getSupportFragmentManager();
        dialog.show(fragmentManager, "ScheduleViewDialog");
    }

    public void setScheduleView(Date startDate, Date endDate){
        LinearLayout view = findViewById(R.id.schedule_view);
        view.removeAllViews();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String startString = df.format(startDate);
        String endString = df.format(endDate);

        TextView dateLabel = new TextView(this);
        dateLabel.setText("Schedule from " + startString + " to " + endString);
        view.addView(dateLabel);

        //todo: iterate through schedules by date
        //todo: change Schedule LocalDate objects to Date objects
    }

    @Override
    public void notifyDataReady() {
        // initialize view with schedule for current day
        setScheduleView(new Date(), new Date());
    }

    @Override
    public void notifyNewDataToSave() {

    }
}
