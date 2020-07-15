package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.AddEmployeePresenter;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

// Ability to add an employee to a shift
public class AddEmployeeActivity extends AppCompatActivity implements Listener {
    private AddEmployeePresenter presenter;
    Intent intent;
    String editingShiftId;
    ArrayList<String> shiftTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        presenter = new AddEmployeePresenter(mAuth.getUid(), database, this);
        setUpView();
    }

    public void openShiftTimeDialog(View view) {
        ShiftTimeDialog dialog = new ShiftTimeDialog();
        FragmentManager fragmentManager = getSupportFragmentManager();
        dialog.show(fragmentManager, "ShiftTimeDialog");
    }

    public void addShiftTime(Time startTime, Time endTime){
        //todo: set times to spinner dropdown
        shiftTimes.add("");
    }

    public void setUpView(){
        // shift type and number needed from AddShiftActivity intent
        intent = getIntent();
        String shiftType = intent.getStringExtra("shiftType");

        int numberNeeded = intent.getIntExtra("numberNeeded", 0);

        // check if shiftId sent - shift is being edited
        if(intent.getStringExtra("shiftId") != null) {
            editingShiftId = intent.getStringExtra("shiftId");
        }
        else {
            editingShiftId = null;
        }

        // set shift type and number needed onto view
        TextView shiftTextView = findViewById(R.id.shift_name);
        shiftTextView.setText(shiftType);

        TextView numberTextView = findViewById(R.id.req_employees);
        numberTextView.setText(String.valueOf(numberNeeded));
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
        // todo: save to cloud (if shift is being edited, need to remove old shift and replace with edited shift)

        // go back to EditScheduleActivity
        Intent intent = new Intent(this, EditScheduleActivity.class);
        startActivity(intent);
    }

    public Boolean checkRequestedOff(User employee, Shift shift) {
        // todo: create logic to check if employee requested time off

        return false; // return true if time was requested off
    }

    @Override
    public void notifyDataReady() {

        setEmployeeTableData();
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

        // calculate view widths
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        float density = this.getResources().getDisplayMetrics().density;
        float px = 16 * density;

        int genWidth = (int)(width - (px * 2)) / 2;

        // set name params
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.width = genWidth;
        nameParams.gravity = Gravity.CENTER_VERTICAL;
        nameParams.setMargins(10, 10, 0, 10);
        params.put("name", nameParams);

        // set userId params
        LinearLayout.LayoutParams idParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        idParams.width = 0;
        idParams.gravity = Gravity.CENTER_VERTICAL;
        params.put("id", idParams);

        // set addToShift params
        LinearLayout.LayoutParams addParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addParams.width = genWidth;
        addParams.gravity = Gravity.CENTER_VERTICAL;
        addParams.gravity = Gravity.RIGHT;
        addParams.setMargins(10, 10, 10, 10);
        params.put("add", addParams);

        return params;
    }

    public void setEmployeeTableData() {
        // get layout parameters
        HashMap<String, LinearLayout.LayoutParams> params = getParams();

        // populate shiftTime options on spinners
        shiftTimes.add("Select");
        if (presenter.getShift().getShiftTimes() != null){
            for(String time : presenter.getShift().getShiftTimes()) {
                shiftTimes.add(time);
            }
        }

        final LinearLayout employeeList = findViewById(R.id.employee_list);

        //add all employees to the list
        int i = 0;
        for (User employee: presenter.getEmployeeList()) {
            LinearLayout separator = createRowSeparator();
            final LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setId(i);
            i++;

            TextView name = new TextView(this);
            name.setLayoutParams(params.get("name"));
            name.setText((employee.getFirstName() + " " + employee.getLastName()));
            row.addView(name);

            TextView userId = new TextView(this);
            userId.setLayoutParams(params.get("id"));
            userId.setText(employee.getUserID());
            userId.setVisibility(View.INVISIBLE);
            row.addView(userId);

            final Switch addToShift = new Switch(this);
            addToShift.setLayoutParams(params.get("add"));
            addToShift.setId(i);
            //if shift is being edited, check if employee was added previously to shift
            if (editingShiftId != null) {
                //todo: create logic to check if employee is already on shift
                //addToShift.setChecked();
            }
            // if switch is checked show spinner with shift times
            addToShift.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LinearLayout updateRow = findViewById(v.getId());
                    View spinner = updateRow.getChildAt(3);
                    if(addToShift.isChecked()) {
                        // show spinner on row
                        spinner.setVisibility(View.VISIBLE);
                    }
                    else {
                        //hide spinner on row
                        spinner.setVisibility(View.INVISIBLE);
                    }
                }
            });
            row.addView(addToShift);

            Spinner addToShiftTime = new Spinner(this);
            addToShiftTime.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_multiple_choice,
                    shiftTimes));
            addToShiftTime.setVisibility(View.INVISIBLE);
            addToShiftTime.setLayoutParams(params.get("add"));
            row.addView(addToShiftTime);

            // Check if employee requested time off
            Boolean timeOff = checkRequestedOff(employee, presenter.getShift());
            if (timeOff) {
                // color set to red if employee requested the time off
                row.setBackgroundColor(Color.parseColor("#ed5a6b"));
            }
            employeeList.addView(separator);
            employeeList.addView(row);
        }
        LinearLayout separator = createRowSeparator();
        employeeList.addView(separator);
    }

    @Override
    public void notifyNewDataToSave() {
        presenter.notifyNewDataToSave();
    }
}
