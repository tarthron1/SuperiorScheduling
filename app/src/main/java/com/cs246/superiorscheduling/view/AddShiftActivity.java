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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);
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

        EditText re = (EditText) findViewById(R.id.number_needed);
        int requiredEmployees = Integer.parseInt(re.getText().toString());

        //todo: create and save shift

        //pass shift type and number needed to AddEmployeeActivity
        Intent intent = new Intent(this, AddEmployeeActivity.class);
        intent.putExtra("shiftType", shiftType);
        intent.putExtra("numberNeeded", requiredEmployees);
        startActivity(intent);
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}
