package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.EditSchedulePresenter;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// The view used to edit the Schedules
public class EditScheduleActivity extends AppCompatActivity implements Listener {

    private EditSchedulePresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    List<String> btnShiftIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        presenter = new EditSchedulePresenter(mAuth.getUid(), database, this);
    }





    // start AddShiftActivity
    public void addShift(View view){
        Intent intent = new Intent(this, AddShiftActivity.class);
        startActivity(intent);
    }

    public void clearSchedule(View view) {
        for (Shift shift: presenter.getShifts()
             ) {
            if (shift.getParentSchedule().equals(presenter.getCurrentSchedule().getScheduleID()))
            for (ShiftTime shiftTime: presenter.getShiftTimes()
                 ) {
                if (shiftTime.getParentShift().equals(shift.getShiftID())){
                    presenter.removeShiftTime(shiftTime);
                }
            }
            presenter.removeShift(shift);
        }

    }

    public void publishSchedule(View view) {
        //todo: set schedule published to true, save schedule to cloud
        presenter.getCurrentSchedule().publishSchedule();
        notifyNewDataToSave();
    }

    // display a list of current shifts that will be added to schedule
    public void setShiftList() {
        LinearLayout shiftList = findViewById(R.id.shift_list);
        int i = 0;
        // add all shifts to the list
        for (Shift shift: presenter.getShifts()) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView shiftType = new TextView(this);
            shiftType.setText(shift.getShiftType());
            row.addView(shiftType);

            TextView date = new TextView(this);
            date.setText(shift.getDate().toString());
            row.addView(date);

            btnShiftIds.add(shift.getShiftID());

            Button edit = new Button(this);
            edit.setText("Edit");
            edit.setId(i);
            edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editShift(btnShiftIds.get(v.getId()));
                }
            });
            row.addView(edit);

            // add row to layout
            shiftList.addView(row);
            i++;
        }
    }

    // Pass shiftId of shift to be edited to AddShiftActivity
    public void editShift(String shiftId) {
        Intent intent = new Intent(this, AddShiftActivity.class);
        intent.putExtra("shiftId", shiftId);
        intent.putExtra("scheduleId", presenter.getCurrentSchedule().getScheduleID());
        startActivity(intent);
    }

    @Override
    public void notifyDataReady() {
        setShiftList();
    }

    @Override
    public void notifyNewDataToSave() {
        //todo what all needs saved to the database from this activity?

    }
}