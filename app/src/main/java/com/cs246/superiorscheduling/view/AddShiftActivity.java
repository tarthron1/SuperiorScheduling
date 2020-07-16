package com.cs246.superiorscheduling.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.presenter.AddShiftPresenter;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

// view the shifts
@RequiresApi(api = Build.VERSION_CODES.O)
public class AddShiftActivity extends AppCompatActivity implements Listener {
    private AddShiftPresenter presenter;
    Intent editShift;
    ZoneId defaultZoneId = ZoneId.systemDefault();
    EditText shiftDateEditText, startTimeEditText, endTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        presenter = new AddShiftPresenter(mAuth.getUid(), database, this);
        // set listeners to date and time TextEdits in layout
        setDateAndTimePickerDialogListeners();
    }

    private void setDateAndTimePickerDialogListeners() {
        shiftDateEditText = findViewById(R.id.shift_date);
        startTimeEditText = findViewById(R.id.begin_time);
        endTimeEditText = findViewById(R.id.end_time);

        shiftDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the DatePicker
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                //create DatePicker dialog
                DatePickerDialog startDatePicker;
                startDatePicker = new DatePickerDialog(AddShiftActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String dateString =  "" + convertDateString(selectedMonth + 1)  + "/" + convertDateString(selectedDay) + "/" + selectedYear;
                        shiftDateEditText.setText(dateString);
                    }
                }, year, month, day);
                startDatePicker.show();
            }
        });

        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddShiftActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeString = "" + convertDateString(hourOfDay) + ":" + convertDateString(minute);
                        startTimeEditText.setText(timeString);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddShiftActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeString = "" + convertDateString(hourOfDay) + ":" + convertDateString(minute);
                        endTimeEditText.setText(timeString);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createShift(View view){
        //get shift data from the view

        EditText shiftEditText = (EditText) findViewById(R.id.shift_type);
        String shiftType = shiftEditText.getText().toString();

        String shiftDateString = shiftDateEditText.getText().toString();
        String startTimeString = startTimeEditText.getText().toString();
        String endTimeString = endTimeEditText.getText().toString();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("kk:mm");
        Date shiftDate = null;
        Date startTime = null;
        Date endTime = null;
        try {
            shiftDate = dateFormat.parse(shiftDateString);
            startTime = timeFormat.parse(startTimeString);
            endTime = timeFormat.parse(endTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText reqEmployeesEditText = (EditText) findViewById(R.id.number_needed);
        int requiredEmployees = Integer.parseInt(reqEmployeesEditText.getText().toString());
        if (presenter.getCurrentShift() == null) {
            Shift shift = new Shift(editShift.getStringExtra("scheduleID"), shiftDate, requiredEmployees, startTime, endTime, shiftType);
            presenter.setCurrentShift(shift);
            presenter.addShift(shift);
        } else {
            presenter.getCurrentShift().setBeginTime(startTime);
            presenter.getCurrentShift().setEndTime(endTime);
            presenter.getCurrentShift().setDate(shiftDate);
            presenter.getCurrentShift().setRequiredEmployees(requiredEmployees);
            presenter.getCurrentShift().setShiftType(shiftType);
        }
        notifyNewDataToSave();

        //pass shift type and number needed to AddEmployeeActivity
        Intent intent = new Intent(this, AddEmployeeActivity.class);
        intent.putExtra("shiftType", shiftType);
        intent.putExtra("numberNeeded", requiredEmployees);
        String shiftId = presenter.getCurrentShift().getShiftID();
        intent.putExtra("shiftId", shiftId);
        startActivity(intent);
    }

    // checks if shift is being edited, if it is it displays shift info
    public void checkEditShift() {
        editShift = getIntent();
        String shiftId = editShift.getStringExtra("shiftId");
        if(shiftId != null) {


            //todo: get shift info by shiftId(done), replace hardcoded values(need conversion to strings)

            for (Shift shift: presenter.getShifts()
                 ) {
                if (shift.getShiftID().equals(shiftId)){
                    presenter.setCurrentShift(shift);
                    break;
                }
            }

            EditText shiftEditText = (EditText) findViewById(R.id.shift_type);
            String shiftType = presenter.getCurrentShift().getShiftType();
            shiftEditText.setText(shiftType);

            EditText dateEditText = (EditText) findViewById(R.id.shift_date);
            Date date = presenter.getCurrentShift().getDate();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
            String strDate = dateFormat.format(date);
            dateEditText.setText(strDate);

            EditText beginTimeEditText = (EditText) findViewById(R.id.begin_time);
            Date beginTime = presenter.getCurrentShift().getBeginTime();
            DateFormat beginTimeFormat = new SimpleDateFormat("hh:mm:ss");
            String strBeginTime = beginTimeFormat.format(beginTime);
            beginTimeEditText.setText(strBeginTime);

            EditText endTimeEditText = (EditText) findViewById(R.id.end_time);
            Date endTime = presenter.getCurrentShift().getEndTime();
            DateFormat endTimeFormat = new SimpleDateFormat("hh:mm:ss");
            String strEndTime = endTimeFormat.format(endTime);
            endTimeEditText.setText(strEndTime);

            EditText reqEmployeesEditText = (EditText) findViewById(R.id.number_needed);
            int reqEmployees = presenter.getCurrentShift().getRequiredEmployees();
            reqEmployeesEditText.setText(String.valueOf(reqEmployees));
        }
    }

    @Override
    public void notifyDataReady() {
        checkEditShift();
    }

    @Override
    public void notifyNewDataToSave() {
        presenter.notifyNewDataToSave();
    }
}
