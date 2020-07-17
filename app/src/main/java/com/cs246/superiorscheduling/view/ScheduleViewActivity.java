package com.cs246.superiorscheduling.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

// The Schedule View
public class ScheduleViewActivity extends AppCompatActivity implements Listener {
    private ScheduleViewPresenter presenter;
    HashMap<String, LinearLayout.LayoutParams> params;

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

    public LinearLayout createRowSeparator() {
        LinearLayout separator = new LinearLayout(this);
        LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        separator.setLayoutParams(sepParams);
        separator.setBackgroundColor(Color.BLACK);

        return separator;
    }

    public HashMap<String, LinearLayout.LayoutParams> getParams() {
        HashMap<String, LinearLayout.LayoutParams> params = new HashMap<>();

        //set time params
        LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        timeParams.gravity = Gravity.CENTER;
        timeParams.width = 300;
        params.put("time", timeParams);

        //set employee params

        return params;
    }

    public void setScheduleView(Date selectedDate){
        LinearLayout view = findViewById(R.id.schedule_view);
        view.removeAllViews();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = df.format(selectedDate);

        //set schedule header
        TextView dateLabel = findViewById(R.id.schedule_dates);
        dateLabel.setText("Schedule containing " + dateString);

        //iterate through schedules, get schedule by date
        for (Schedule schedule: presenter.getSchedules()) {

            System.out.println(selectedDate);
            System.out.println(schedule.getStartDay());
            System.out.println(schedule.getEndDay());

            if ((selectedDate.after(schedule.getStartDay()) && selectedDate.before(schedule.getEndDay())) ||
                    selectedDate.equals(schedule.getStartDay()) ||
                    selectedDate.equals(schedule.getEndDay())) {
                if (schedule.isPublished()) {
                    presenter.setSelectedSchedule(schedule);

                    //set day layout
                    LinearLayout dayRow = new LinearLayout(this);

                    for (Shift shift : presenter.getShifts()) {
                        LinearLayout separator = createRowSeparator();
                        //set shift/day header
                        TextView shiftLabel = new TextView(this);
                        shiftLabel.setText(shift.getShiftType() + " for " + df.format(shift.getDate()));
                        shiftLabel.setBackgroundColor(Color.parseColor("#06d6a0"));
                        dayRow.addView(shiftLabel);

                    //set shift layout
                    LinearLayout shiftRow = new LinearLayout(this);
                    for (ShiftTime shiftTime : presenter.getShiftTimesBySchedule()) {
                        //set time header
                        TextView timeLabel = new TextView(this);
                        timeLabel.setText(formatTime(shiftTime.getStartTime(), shiftTime.getEndTime()));
                        timeLabel.setBackgroundColor(Color.parseColor("#33bbff"));
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
    }

    private String formatTime(Date sTime, Date eTime) {
        DateFormat formatTime = new SimpleDateFormat("hh:mm a");
        return formatTime.format(sTime) + " - " + formatTime.format(eTime);
    }

    @Override
    public void notifyDataReady() {
        // initialize view with schedule for current day
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date date = c.getTime();

        setScheduleView(date);
    }

    @Override
    public void notifyNewDataToSave() {

    }
}
