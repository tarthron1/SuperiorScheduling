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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.AddEmployeePresenter;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// Ability to add an employee to a shift
public class AddEmployeeActivity extends AppCompatActivity implements Listener {
    private AddEmployeePresenter presenter;
    private Intent intent;
    private String editingShiftId;
    private ArrayList<String> shiftTimes = new ArrayList<>();
    private LinearLayout employeeList;

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

    public void addShiftTime(Date startTime, Date endTime){
        ShiftTime shiftTime = new ShiftTime(startTime, endTime, presenter.getCurrentShift());
        presenter.addShiftTime(shiftTime);
        presenter.getCurrentShift().addShiftTime(shiftTime);
        notifyNewDataToSave();
        notifyDataReady();
    }

    public void setUpView(){
        // shift type and number needed from AddShiftActivity intent
        intent = getIntent();
        String shiftType = intent.getStringExtra("shiftType");

        int numberNeeded = intent.getIntExtra("numberNeeded", 0);

        editingShiftId = intent.getStringExtra("shiftId");

        // set shift type and number needed onto view
        TextView shiftTextView = findViewById(R.id.shift_name);
        shiftTextView.setText(shiftType);

        TextView numberTextView = findViewById(R.id.req_employees);
        numberTextView.setText(String.valueOf(numberNeeded));
    }

    public void addToShift(View view) {
        employeeList = findViewById(R.id.employee_list);
        // iterate through each row on the list, get employee id and shift time
        for (int i = 1; i < employeeList.getChildCount() - 1; i += 2){
            LinearLayout row = (LinearLayout) employeeList.getChildAt(i);
            View userIdView = row.getChildAt(1);
            TextView currentUserId = (TextView) userIdView;

            View currentShiftSpinner = row.getChildAt(2);

            // check if spinner option is shift time, add employee id to ShiftTime
            if (((Spinner) currentShiftSpinner).getSelectedItem().toString().equals("Not on Shift")) {
                //Set employeesOnShift list in ShiftTime object
                for (ShiftTime shiftTime: presenter.getShiftTimesByShift()
                     ) {
                    if (shiftTime.getEmployeesOnShift().contains(currentUserId.getText().toString())){
                        shiftTime.removeEmployee(currentUserId.getText().toString());
                    }
                }

            } else {
                for (ShiftTime shiftTime: presenter.getShiftTimesByShift()
                     ) {
                    if (formatTime(shiftTime.getStartTime(), shiftTime.getEndTime()).equals(((Spinner) currentShiftSpinner).getSelectedItem().toString())){
                        if (!shiftTime.getEmployeesOnShift().contains(currentUserId.getText().toString())){
                            shiftTime.addEmployee(currentUserId.getText().toString());
                        }
                    } else if (shiftTime.getEmployeesOnShift().contains(currentUserId.getText().toString())){
                        shiftTime.removeEmployee(currentUserId.getText().toString());
                    }
                }
            }
        }
        //  save to cloud (if shift is being edited, need to remove old shift and replace with edited shift)
        notifyNewDataToSave();
        // update table
        setEmployeeTableData();
        Toast.makeText(this, ("Changes Saved."),
                Toast.LENGTH_SHORT).show();
    }

    public Boolean checkRequestedOff(User employee, Shift shift) {
        //  logic to check if employee requested time off
        ArrayList<Request> employeeRequests = new ArrayList<>();
        for (Request request: presenter.getRequests()
             ) {
            if (request.getRequesterID().equals(employee.getUserID())){
                employeeRequests.add(request);
            }
        }
        Date shiftDate = shift.getDate();

        for (Request request: employeeRequests
        ) {
            if (request.getShiftID() == shift.getShiftID()){
                return true;
            } else if (request.getDate().equals(shiftDate)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void notifyDataReady() {
        for (Shift shift: presenter.getShifts()
             ) {
            if (shift.getShiftID().equals(editingShiftId)){
                presenter.setCurrentShift(shift);
            }
        }
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
        nameParams.width = genWidth - 150;
        nameParams.gravity = Gravity.CENTER_VERTICAL;
        nameParams.setMargins(10, 10, 0, 10);
        params.put("name", nameParams);

        // set userId params
        LinearLayout.LayoutParams idParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        idParams.width = 100;
        idParams.gravity = Gravity.CENTER_VERTICAL;
        params.put("id", idParams);

        // set addToShift params
        LinearLayout.LayoutParams addParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addParams.width = genWidth;
        addParams.gravity = Gravity.CENTER_VERTICAL;
        addParams.gravity = Gravity.END;
        addParams.setMargins(10, 10, 10, 10);
        params.put("add", addParams);

        return params;
    }

    public void setShiftTimesToSpinner() {
        // populate shiftTime options on spinners
        shiftTimes.clear();
        shiftTimes.add("Not on Shift");
        if (presenter.getShiftTimesByShift() != null){
            for (ShiftTime shiftTime: presenter.getShiftTimesByShift()
            ) {
                // format time output
                shiftTimes.add(formatTime(shiftTime.getStartTime(), shiftTime.getEndTime()));
            }
        }
    }

    public void setEmployeeTableData() {
        // get layout parameters
        HashMap<String, LinearLayout.LayoutParams> params = getParams();
        setShiftTimesToSpinner();

        employeeList = findViewById(R.id.employee_list);
        employeeList.removeAllViews();

        //add all employees to the list
        for (User employee: presenter.getEmployeeList()) {
            LinearLayout separator = createRowSeparator();
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            // set employee name to row
            TextView name = new TextView(this);
            name.setLayoutParams(params.get("name"));
            name.setText((employee.getFirstName() + " " + employee.getLastName()));
            row.addView(name);

            // set userId to row
            TextView userId = new TextView(this);
            userId.setLayoutParams(params.get("id"));
            userId.setText(employee.getUserID());
            userId.setVisibility(View.INVISIBLE);
            userId.setTextSize(1);
            row.addView(userId);

            // set spinner dropdown with ShiftTimes to row
            Spinner addToShiftTime = new Spinner(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    shiftTimes);
            addToShiftTime.setAdapter(adapter);
            addToShiftTime.setVisibility(View.VISIBLE);
            addToShiftTime.setLayoutParams(params.get("add"));
            // set selected dropdown option based on if employee was previously added
            if (editingShiftId != null) {
                // logic to check if employee is already on shift
                for (ShiftTime shiftTime: presenter.getShiftTimesByShift()) {
                    if(shiftTime.getEmployeesOnShift() != null) {
                        if (shiftTime.getEmployeesOnShift().contains(employee.getUserID())) {
                            String currentStartTime = shiftTime.getStartTime().toString();
                            int spinnerPos = adapter.getPosition(currentStartTime);
                            addToShiftTime.setSelection(spinnerPos);
                        }
                        else {
                            addToShiftTime.setSelection(0);
                        }
                    }
                }
            }
            row.addView(addToShiftTime);

            // Check if employee requested time off
            Boolean timeOff = checkRequestedOff(employee, presenter.getCurrentShift());
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

    private String formatTime(Date sTime, Date eTime) {
        DateFormat formatTime = new SimpleDateFormat("hh:mm a");
        return formatTime.format(sTime) + " - " + formatTime.format(eTime);
    }

    @Override
    public void notifyNewDataToSave() {
        presenter.notifyNewDataToSave();
    }
}
