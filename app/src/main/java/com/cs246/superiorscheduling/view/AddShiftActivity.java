package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// Fragment to view the shifts
@RequiresApi(api = Build.VERSION_CODES.O)
public class AddShiftActivity extends AppCompatActivity implements Listener {
    private AddShiftPresenter presenter;
    Intent editShift;
    ZoneId defaultZoneId = ZoneId.systemDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        presenter = new AddShiftPresenter(mAuth.getUid(), database, this);

        //check if shift is being edited and adds editable info to view
        checkEditShift();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createShift(View view){
        //get shift data from the view
        EditText shiftEditText = (EditText) findViewById(R.id.shift_type);
        String shiftType = shiftEditText.getText().toString();

        EditText dateEditText = (EditText) findViewById(R.id.shift_date);
        LocalDate localDate = LocalDate.parse(dateEditText.getText().toString(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        //converting localDate to date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        //https://developer.android.com/reference/android/widget/DatePicker

//        EditText beginTimeEditText = (EditText) findViewById(R.id.begin_time);
//        LocalTime beginTime = LocalTime.parse(beginTimeEditText.getText().toString(), DateTimeFormatter.ofPattern("HH:mm"));
//        //converting LocalTime to Date object
//        Instant startInstant = beginTime.atDate(LocalDate.from(beginTime)).atZone(ZoneId.systemDefault()).toInstant();
//        Date startTime = Date.from(startInstant);
//        //https://developer.android.com/reference/android/widget/TimePicker
//
//        EditText endTimeEditText = (EditText) findViewById(R.id.end_time);
//        LocalTime endTime = LocalTime.parse(endTimeEditText.getText().toString(), DateTimeFormatter.ofPattern("HH:mm"));
//        //converting LocalTime to Date object
//        Instant endInstant = endTime.atDate(LocalDate.from(endTime)).atZone(ZoneId.systemDefault()).toInstant();
//        Date finishTime = Date.from(endInstant);

        String amString = "09:00 AM";
        String pmString = "05:00 PM";
        DateFormat time = new SimpleDateFormat("hh:mm a");
        Date startTime = null;
        Date finishTime = null;
        try {
            startTime = time.parse(amString);
            finishTime = time.parse(pmString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText reqEmployeesEditText = (EditText) findViewById(R.id.number_needed);
        int requiredEmployees = Integer.parseInt(reqEmployeesEditText.getText().toString());

        //todo: create and save shift
        Shift shift = new Shift(editShift.getStringExtra("scheduleID"),date, requiredEmployees, startTime, finishTime, shiftType);
        presenter.setCurrentShift(shift);
        presenter.addShift(shift);
        notifyNewDataToSave();

        //pass shift type and number needed to AddEmployeeActivity
        Intent intent = new Intent(this, AddEmployeeActivity.class);
        intent.putExtra("shiftType", shiftType);
        intent.putExtra("numberNeeded", requiredEmployees);
        // check if editing shift, send old shift id to next activity
        if(editShift != null) {
            String shiftId = editShift.getStringExtra("shiftId");
            intent.putExtra("shiftId", shiftId);
        }
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
            DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyy");
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
            reqEmployeesEditText.setText(reqEmployees);
        }
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {
        presenter.notifyNewDataToSave();
    }
}
