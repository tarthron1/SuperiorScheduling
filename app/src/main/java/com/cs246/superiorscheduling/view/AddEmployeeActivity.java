package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.Listener;

import java.util.UUID;

// Ability to add an employee to a shift
public class AddEmployeeActivity extends AppCompatActivity implements Listener {
    private Shift shift; // todo: get shift object from AddShiftActivity, or from cloud?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        // shift type and number needed from AddShiftActivity intent
        Intent intent = getIntent();
        String shiftType = intent.getStringExtra("shiftType");

        String numberNeeded = intent.getStringExtra("numberNeeded");

        // set shift type and number needed onto view
        TextView shiftTextView = findViewById(R.id.shift_name);
        shiftTextView.setText(shiftType);

        TextView numberTextView = findViewById(R.id.req_employees);
        numberTextView.setText(numberNeeded);
    }

    public void addToShift(View view) {
        LinearLayout employeeList = findViewById(R.id.employee_list);
        // iterate through each row on the list, get employee id and onShift switch
        for (int i = 0; i < employeeList.getChildCount(); i++){
            LinearLayout row = findViewById(i);
            View id = row.getChildAt(1);
            View sw = row.getChildAt(2);

            // check if switch is activated, if checked add employee id to ShiftTime
            Boolean onShift = ((Switch) sw).isChecked();
            if (onShift) {
                // todo: Set employeesOnShift list in ShiftTime object

            }
        }
        // todo: save to cloud
    }

    public Boolean checkRequestedOff(User employee, Shift shift) {
        // todo: create logic to check if employee requested time off
        return false; // return true if time was requested off
    }

    @Override
    public void notifyDataReady() {
        setEmployeeTableData();
    }

    public void setEmployeeTableData() {
        LinearLayout employeeList = findViewById(R.id.employee_list);

        //add all employees to the list
        int i = 0;
        for (User employee: list) { // todo: get list of employees
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setId(i);
            i++;

            TextView name = new TextView(this);
            name.setText((employee.getFirstName() + " " + employee.getLastName()));
            row.addView(name);

            TextView userId = new TextView(this);
            userId.setText(employee.getUserID());
            userId.setVisibility(View.INVISIBLE);
            row.addView(name);

            Switch addToShift = new Switch(this);
            row.addView(addToShift);

            // Check if employee requested time off
            Boolean timeOff = checkRequestedOff(employee, shift);
            if (timeOff) {
                // color set to red if employee requested the time off
                row.setBackgroundColor(Color.RED);
            }
            employeeList.addView(row);
        }
    }

    @Override
    public void notifyNewDataToSave() {

    }
}
