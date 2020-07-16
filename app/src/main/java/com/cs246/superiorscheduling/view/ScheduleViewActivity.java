package com.cs246.superiorscheduling.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.ScheduleViewPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public void setScheduleView(Date startDate){
        LinearLayout view = findViewById(R.id.schedule_view);
        view.removeAllViews();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String startString = df.format(startDate);

        //set schedule header
        TextView dateLabel = findViewById(R.id.schedule_dates);
        dateLabel.setText("Schedule containing " + startString);

        //iterate through schedules, get schedule by date
        for (Schedule schedule: presenter.getSchedules()) {
            if ((startDate.after(schedule.getStartDay()) && startDate.before(schedule.getEndDay())) ||
                    startDate.equals(schedule.getStartDay()) ||
                    startDate.equals(schedule.getEndDay())) {

                presenter.setSelectedSchedule(schedule);
                //set day layout
                LinearLayout dayRow = new LinearLayout(this);

                for (Shift shift : presenter.getShifts()) {
                    //set shift/day header
                    TextView shiftLabel = new TextView(this);
                    shiftLabel.setText(shift.getShiftType() + " for " + df.format(shift.getDate()));
                    dayRow.addView(shiftLabel);

                    //set shift layout
                    LinearLayout shiftRow = new LinearLayout(this);
                    for (ShiftTime shiftTime : presenter.getShiftTimes()) {
                        //set time header
                        TextView timeLabel = new TextView(this);
                        timeLabel.setText(formatTime(shiftTime.getStartTime(), shiftTime.getEndTime()));
                        shiftRow.addView(timeLabel);

                        //set employee layout
                        LinearLayout employeesOnShift = new LinearLayout(this);
                        for (String employeeId : shiftTime.getEmployeesOnShift()) {
                            for (User employee : presenter.getEmployees())
                                if (employee.getUserID().equals(employeeId)) {
                                    //set each employee name to employee layout
                                    TextView employeeName = new TextView(this);
                                    employeeName.setText(employee.getFirstName() + " " + employee.getLastName());
                                    employeesOnShift.addView(employeeName);
                                }
                        }
                        shiftRow.addView(employeesOnShift);
                    }
                    dayRow.addView(shiftRow);
                }
                view.addView(dayRow);
            }
        }
    }


    private String formatTime(Date sTime, Date eTime) {
        DateFormat formatTime = new SimpleDateFormat("hh:mm a");
        return formatTime.format(sTime) + " - " + formatTime.format(eTime);
    }

    @Override
    public void notifyDataReady() {
        // initialize view with schedule for current day
        setScheduleView(new Date());
    }

    @Override
    public void notifyNewDataToSave() {

    }
}
