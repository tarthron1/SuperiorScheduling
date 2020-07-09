package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Fragment to view the shifts
public class AddShiftActivity extends AppCompatActivity implements Listener {
    Intent editShift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        //check if shift is being edited and adds editable info to view
        checkEditShift();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createShift(View view){
        //get shift data from the view
        EditText shiftEditText = (EditText) findViewById(R.id.shift_type);
        String shiftType = shiftEditText.getText().toString();

        EditText dateEditText = (EditText) findViewById(R.id.shift_date);
        LocalDate date = LocalDate.parse(dateEditText.getText().toString(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        //https://developer.android.com/reference/android/widget/DatePicker

        EditText beginTimeEditText = (EditText) findViewById(R.id.begin_time);
        LocalTime beginTime = LocalTime.parse(beginTimeEditText.getText().toString(), DateTimeFormatter.ofPattern("HH:mm"));
        //https://developer.android.com/reference/android/widget/TimePicker

        EditText endTimeEditText = (EditText) findViewById(R.id.end_time);
        LocalTime endTime = LocalTime.parse(endTimeEditText.getText().toString(), DateTimeFormatter.ofPattern("HH:mm"));

        EditText reqEmployeesEditText = (EditText) findViewById(R.id.number_needed);
        int requiredEmployees = Integer.parseInt(reqEmployeesEditText.getText().toString());

        //todo: create and save shift

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
        if(editShift != null) {
            String shiftId = editShift.getStringExtra("shiftId");

            //todo: get shift info by shiftId, replace hardcoded values
            EditText shiftEditText = (EditText) findViewById(R.id.shift_type);
            String shiftType = "Shift Type";
            shiftEditText.setText(shiftType);

            EditText dateEditText = (EditText) findViewById(R.id.shift_date);
            String date = "mm/dd/yyyy";
            dateEditText.setText(date);

            EditText beginTimeEditText = (EditText) findViewById(R.id.begin_time);
            String beginTime = "HH:mm";
            beginTimeEditText.setText(beginTime);

            EditText endTimeEditText = (EditText) findViewById(R.id.end_time);
            String endTime = "HH:mm";
            endTimeEditText.setText(endTime);

            EditText reqEmployeesEditText = (EditText) findViewById(R.id.number_needed);
            String reqEmployees = "#";
            reqEmployeesEditText.setText(reqEmployees);
        }
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}
