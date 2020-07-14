package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.presenter.EditSchedulePresenter;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// The view used to edit the Schedules
public class EditScheduleActivity extends AppCompatActivity implements Listener {

    private EditSchedulePresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    ArrayList<String> btnShiftIds = new ArrayList<>();

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
        if (presenter.getCurrentSchedule() == null) {
            EditText startDateEditText = findViewById(R.id.editTextDate4);
            EditText endDateEditText = findViewById(R.id.editTextDate5);
            String startDateString = startDateEditText.getText().toString();
            String endDateString = endDateEditText.getText().toString();
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDateString);
                endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Schedule newSchedule = new Schedule(startDate, endDate);
            presenter.setCurrentSchedule(newSchedule);
            notifyNewDataToSave();
        }
        Intent intent = new Intent(this, AddShiftActivity.class);
        intent.putExtra("scheduleID", presenter.getCurrentSchedule().getScheduleID());
        startActivity(intent);
    }
/*
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
*/
    public void publishSchedule(View view) {
        //todo: set schedule published to true, save schedule to cloud
       // presenter.getCurrentSchedule().publishSchedule();
        notifyNewDataToSave();
    }

    public LinearLayout createRowSeparator() {
        LinearLayout separator = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        separator.setLayoutParams(params);
        separator.setBackgroundColor(Color.BLACK);

        return separator;
    }

    // set up layout parameters
    public HashMap<String, LinearLayout.LayoutParams> getParams() {
        HashMap<String, LinearLayout.LayoutParams> params = new HashMap<>();

        // calculate button view width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        float density = this.getResources().getDisplayMetrics().density;
        float px = 16 * density;

        int btnWidth = (int)(width - (px * 2)) / 5;

        // calculate other view widths
        int genWidth = (width - btnWidth) / 2;

        // set button params
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.width = btnWidth;
        btnParams.gravity = Gravity.CENTER_VERTICAL;
        btnParams.gravity = Gravity.RIGHT;
        btnParams.setMargins(10, 10, 10, 10);
        params.put("button", btnParams);

        // set shiftType params
        LinearLayout.LayoutParams shiftParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        shiftParams.width = genWidth;
        shiftParams.gravity = Gravity.CENTER_VERTICAL;
        shiftParams.setMargins(10, 10, 10, 10);
        params.put("shift", shiftParams);

        // set date params
        LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dateParams.width = genWidth;
        dateParams.gravity = Gravity.CENTER;
        params.put("date", dateParams);

        return params;
    }

    // display a list of current shifts that will be added to schedule
    public void setShiftList() {
        HashMap<String, LinearLayout.LayoutParams> params = getParams();
        LinearLayout shiftList = findViewById(R.id.shift_list);
        int i = 0;
        // add all shifts to the list
        for (Shift shift: presenter.getShifts()) {
            LinearLayout separator = createRowSeparator();
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView shiftType = new TextView(this);
            shiftType.setLayoutParams(params.get("shift"));
            shiftType.setText(shift.getShiftType());
            row.addView(shiftType);

            TextView date = new TextView(this);
            date.setLayoutParams(params.get("date"));
            date.setText(shift.getDate().toString());
            row.addView(date);

            btnShiftIds.add(shift.getShiftID());

            Button edit = new Button(this);
            edit.setLayoutParams(params.get("button"));
            edit.setText("Edit");
            edit.setId(i);
            edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editShift(btnShiftIds.get(v.getId()));
                }
            });
            row.addView(edit);

            // add row to layout
            shiftList.addView(separator);
            shiftList.addView(row);
            i++;
        }
        LinearLayout separator = createRowSeparator();
        shiftList.addView(separator);
    }

    // Pass shiftId of shift to be edited to AddShiftActivity
    public void editShift(String shiftId) {
        Intent intent = new Intent(this, AddShiftActivity.class);
        intent.putExtra("shiftId", shiftId);
        intent.putExtra("scheduleID", presenter.getCurrentSchedule().getScheduleID());
        startActivity(intent);
    }

    @Override
    public void notifyDataReady() {
        if (presenter.getSchedules().size() != 0) {
            for (Schedule schedule : presenter.getSchedules()
            ) {
                if (!schedule.isPublished()) {
                    presenter.setCurrentSchedule(schedule);
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    EditText startDateEditText = findViewById(R.id.editTextDate4);
                    EditText endDateEditText = findViewById(R.id.editTextDate5);
                    startDateEditText.setText(format.format(presenter.getCurrentSchedule().getStartDay()));
                    endDateEditText.setText(format.format(presenter.getCurrentSchedule().getEndDay()));
                }
            }
        }
        setShiftList();
    }

    @Override
    public void notifyNewDataToSave() {
        //todo what all needs saved to the database from this activity?
        presenter.notifyNewDataToSave();

    }
}