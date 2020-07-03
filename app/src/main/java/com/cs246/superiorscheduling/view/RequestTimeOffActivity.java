package com.cs246.superiorscheduling.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.RequestTimeOffPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class RequestTimeOffActivity extends AppCompatActivity implements Listener {

    RequestTimeOffPresenter presenter = new RequestTimeOffPresenter();
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseCurrentUser, databaseScheduleList, databaseShiftList, databaseRequestList;
    Spinner dropdown;
    String date = null;
    String reason = null;
    String shift = null;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_off);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseCurrentUser = database.getReference().child("users").child(Objects.requireNonNull(mAuth.getUid()));
        getDatabaseData();
    }

    private void getDatabaseData() {
        databaseCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.setCurrentUser(snapshot.getValue(User.class));
                databaseScheduleList = database.getReference().child("schedule").child(presenter.getCurrentUser().getCompanies().get(0));
                databaseRequestList = database.getReference().child("request").child(presenter.getCurrentUser().getUserID());

                databaseRequestList.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot request: snapshot.getChildren()
                             ) {
                            presenter.addRequest(request.getValue(Request.class));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                databaseScheduleList.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final ArrayList<Schedule> scheduleList = new ArrayList<>();
                        for (DataSnapshot schedule: snapshot.getChildren()
                             ) {
                            scheduleList.add(schedule.getValue(Schedule.class));
                        }
                        presenter.setScheduleList(scheduleList);
                        databaseShiftList = database.getReference().child("shift").child(presenter.getCurrentUser().getCompanies().get(0));
                        databaseShiftList.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ArrayList<Shift> tempShiftList = new ArrayList<>();
                                for (DataSnapshot shift: snapshot.getChildren()
                                     ) {
                                    tempShiftList.add(shift.getValue(Shift.class));
                                }
                                presenter.setShiftList(tempShiftList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        notifyDataReady();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitRequest(View view) {
        EditText dateEditText = findViewById(R.id.request_date);
        date = dateEditText.getText().toString();
        LocalDate localDate= LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        reason = findViewById(R.id.request_reason).toString();
        shift = findViewById(R.id.request_shift).toString();
        Request timeOffRequest = new Request(presenter.getCurrentUser(), localDate, shift, reason);
        presenter.addRequest(timeOffRequest);
        notifyNewDataToSave();
    }
    public void populateSpinner(){
        dropdown = (Spinner) findViewById(R.id.request_shift);
        // set shift list to dropdown
        // todo Iterate through the shift list get the shiftType. First item in list needs to be all day or something similar.
        //dropdown.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                //android.R.layout.simple_spinner_dropdown_item,
                //presenter.getShiftNames()));
    }

    public void viewSubmittedRequests() {
        LinearLayout requests = findViewById(R.id.submitted_requests);
        for (Request request : list) { //todo: get list of requests for user
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            // Set date to row
            TextView date = new TextView(this);
            date.setText(request.getDate().toString());
            row.addView(date);

            // Check if there is a shift for the request
            if (request.getShiftID() != null) {
                // Set shiftType to row
                TextView shift = new TextView(this);
                shift.setText("Shift Type"); //todo: get shift type for submitted requests
                row.addView(shift);
            }

            // Check if there is a reason for the request
            if (request.getReason() != null) {
                // Set reason to row
                TextView reason = new TextView(this);
                reason.setText(request.getReason());
                row.addView(reason);
            }
            requests.addView(row);
        }
    }

    @Override
    public void notifyDataReady() {
        populateSpinner();
        viewSubmittedRequests();
    }

    @Override
    public void notifyNewDataToSave() {
        databaseRequestList.setValue(presenter.getUserRequests());

    }
}
