package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.AddEmployeePresenter;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Ability to add an employee to a shift
public class AddEmployeeActivity extends AppCompatActivity implements Listener {
    private AddEmployeePresenter presenter = new AddEmployeePresenter();
    private FirebaseDatabase database;
    Intent intent;
    String editingShiftId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        setUpView();
        getDatabaseData();
    }

    public void setUpView(){
        // shift type and number needed from AddShiftActivity intent
        intent = getIntent();
        String shiftType = intent.getStringExtra("shiftType");

        String numberNeeded = intent.getStringExtra("numberNeeded");

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
        numberTextView.setText(numberNeeded);
    }

    public void getDatabaseData(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        DatabaseReference currentUserReference = database.getReference().child("users").child(mAuth.getUid());
        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.setCurrentUser(snapshot.getValue(User.class));
                getCompaniesFromDatabase();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCompaniesFromDatabase() {
        DatabaseReference companyReference = database.getReference().child("companies").child(presenter.getCurrentUser().getCompanies().get(0));
        companyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.setCurrentCompany(snapshot.getValue(Company.class));
                getUsersAndRequestsFromDatabase();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getUsersAndRequestsFromDatabase() {
        DatabaseReference usersReference = database.getReference().child("users");

        for (String employeeID: presenter.getCurrentCompany().getActiveEmployeeList()){
            DatabaseReference employeeRequestReference = database.getReference().child("request").child(employeeID);
            employeeRequestReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot request: snapshot.getChildren()
                    ) {
                        Request requestFromDatabase = request.getValue(Request.class);
                        presenter.addRequest(requestFromDatabase);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            usersReference = database.getReference().child("users").child(employeeID);
            usersReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    presenter.addEmployee(snapshot.getValue(User.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

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

    public void setEmployeeTableData() {
        LinearLayout employeeList = findViewById(R.id.employee_list);

        //add all employees to the list
        int i = 0;
        for (User employee: presenter.getEmployeeList()) {
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
            //if shift is being edited, check if employee was added previously to shift
            if (editingShiftId != null) {
                //todo: create logic to check if employee is already on shift
                //addToShift.setChecked();
            }
            row.addView(addToShift);

            // Check if employee requested time off
            Boolean timeOff = checkRequestedOff(employee, presenter.getShift());
            if (timeOff) {
                // color set to red if employee requested the time off
                row.setBackgroundColor(Color.RED);
            }
            employeeList.addView(row);
        }
    }

    @Override
    public void notifyNewDataToSave() {
        DatabaseReference shiftReference = database.getReference().child("shift").child(presenter.getCurrentCompany().getCompanyID());
        DatabaseReference shiftTimeReference = database.getReference().child("shiftTime").child(presenter.getCurrentCompany().getCompanyID());
        shiftReference.setValue(presenter.getShift());
        shiftTimeReference.setValue(presenter.getShiftTime());
    }
}
