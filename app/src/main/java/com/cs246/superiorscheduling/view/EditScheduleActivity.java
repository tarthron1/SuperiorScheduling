package com.cs246.superiorscheduling.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.presenter.EditSchedulePresenter;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

// The view used to edit the Schedules
public class EditScheduleActivity extends AppCompatActivity implements Listener {

    private EditSchedulePresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    ArrayList<String> btnShiftIds = new ArrayList<>();
    EditText startDateEditText, endDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        presenter = new EditSchedulePresenter(mAuth.getUid(), database, this);
        // set listeners to date TextEdits in layout
        setDatePickerDialogListeners();
    }

    public void setDatePickerDialogListeners() {
        startDateEditText = findViewById(R.id.schedule_startDate);
        endDateEditText = findViewById(R.id.schedule_endDate);

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the DatePicker
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                //create DatePicker dialog
                DatePickerDialog startDatePicker;
                startDatePicker = new DatePickerDialog(EditScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String dateString =  "" + convertDateString(selectedMonth + 1)  + "/" + convertDateString(selectedDay) + "/" + selectedYear;
                        startDateEditText.setText(dateString);
                    }
                }, year, month, day);
                startDatePicker.show();
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the DatePicker
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                //create DatePicker dialog
                DatePickerDialog startDatePicker;
                startDatePicker = new DatePickerDialog(EditScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String dateString =  "" + convertDateString(selectedMonth + 1)  + "/" + convertDateString(selectedDay) + "/" + selectedYear;
                        endDateEditText.setText(dateString);
                    }
                }, year, month, day);
                startDatePicker.show();
            }
        });
    }

    private String convertDateString(int date) {
        if(date < 10) {
            return "0" + date;
        }
        else {
            return String.valueOf(date);
        }
    }

    // start AddShiftActivity
    public void addShift(View view){
        if (presenter.getCurrentSchedule() == null) {
            startDateEditText = findViewById(R.id.schedule_startDate);

            startDateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //To show current date in the DatePicker
                    Calendar currentDate = Calendar.getInstance();
                    int year = currentDate.get(Calendar.YEAR);
                    int month = currentDate.get(Calendar.MONTH);
                    int day = currentDate.get(Calendar.DAY_OF_MONTH);

                    //create DatePicker dialog
                    DatePickerDialog startDatePicker;
                    startDatePicker = new DatePickerDialog(EditScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                            String dateString = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            startDateEditText.setText(dateString);
                        }
                    }, year, month, day);
                    startDatePicker.show();
                }
            });

            EditText endDateEditText = findViewById(R.id.schedule_endDate);
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
        this.finish();
    }

    public void clearSchedule(View view) {
/*        for (Shift shift: presenter.getShifts()) {
            if (shift.getParentSchedule().equals(presenter.getCurrentSchedule().getScheduleID()))
            for (ShiftTime shiftTime: presenter.getShiftTimes()
                 ) {
                if (shiftTime.getParentShift().equals(shift.getShiftID())){
                    presenter.removeShiftTime(shiftTime);
                }
            }
            presenter.removeShift(shift);
        }*/

        //iterate over shifts to remove shiftTimes and shifts that are on current schedule
        List<Shift> removeShifts = new ArrayList<>();
        removeShifts.addAll(presenter.getShifts());
        Iterator<Shift> i = removeShifts.iterator();
        while (i.hasNext()) {
            Shift s = i.next();
            //iterate over shiftTimes and remove
            List<ShiftTime> removeShiftTimes = new ArrayList<>();
            removeShiftTimes.addAll(presenter.getShiftTimes());
            Iterator<ShiftTime> j = removeShiftTimes.iterator();
            if (s.getParentSchedule().equals(presenter.getCurrentSchedule().getScheduleID())) {
                while (j.hasNext()) {
                    ShiftTime st = j.next();
                    if (st.getParentShift().equals(s.getShiftID())) {
                        //remove shiftTime
                        presenter.removeShiftTime(st);
                    }
                }
                //remove shift
                presenter.removeShift(s);
            }
        }


        notifyNewDataToSave();
        // reload view
        notifyDataReady();

        // confirm deletion
        Toast.makeText(this, ("Changes Saved."),
                Toast.LENGTH_SHORT).show();
    }

    public void publishSchedule(View view) {
        presenter.getCurrentSchedule().publishSchedule();
        notifyNewDataToSave();
        notifyDataReady();

        Toast.makeText(this, ("Changes Saved."),
                Toast.LENGTH_SHORT).show();
        this.finish();
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
        shiftParams.width = genWidth / 2;
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
        shiftList.removeAllViews();
        int i = 0;
        // add all shifts to the list
        for (Shift shift: presenter.getShiftsBySchedule()) {
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
                    EditText startDateEditText = findViewById(R.id.schedule_startDate);
                    EditText endDateEditText = findViewById(R.id.schedule_endDate);
                    startDateEditText.setText(format.format(presenter.getCurrentSchedule().getStartDay()));
                    endDateEditText.setText(format.format(presenter.getCurrentSchedule().getEndDay()));
                }
            }
        }
        setShiftList();
    }

    @Override
    public void notifyNewDataToSave() {
        presenter.notifyNewDataToSave();
    }
}