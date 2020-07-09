package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.Schedule;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.model.ShiftTime;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.EditSchedulePresenter;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.presenter.Listener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// The view used to edit the Schedules
public class EditScheduleActivity extends AppCompatActivity implements Listener {

    private EditSchedulePresenter presenter = new EditSchedulePresenter();
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseUser, databaseSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        getDataFromDatabase();
    }

    private void getDataFromDatabase() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseUser = database.getReference().child("users").child(mAuth.getUid());
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.setCurrentUser(snapshot.getValue(User.class));
                getSchedulesFromDatabase();
                getCompanyFromDatabase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCompanyFromDatabase() {
        DatabaseReference databaseCompany = database.getReference().child("companies").child(presenter.getCurrentUser().getCompanies().get(0));
        databaseCompany.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.setCurrentCompany(snapshot.getValue(Company.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getSchedulesFromDatabase() {
            final ArrayList<Schedule> schedules = new ArrayList<>();
            databaseSchedules = database.getReference().child("schedule").child(presenter.getCurrentUser().getCompanies().get(0));
            databaseSchedules.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot schedule: snapshot.getChildren()
                         ) {
                        schedules.add(schedule.getValue(Schedule.class));
                    }
                    presenter.setSchedules(schedules);
                    getShiftsFromDatabase();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    private void getShiftsFromDatabase() {
        final ArrayList<Shift> shifts = new ArrayList<>();
        DatabaseReference databaseShifts = database.getReference().child("shift").child(presenter.getCurrentUser().getCompanies().get(0));
        databaseShifts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shift: snapshot.getChildren()
                     ) {
                    shifts.add(shift.getValue(Shift.class));

                }
                presenter.setShifts(shifts);
                getShiftTimeFromDatabase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getShiftTimeFromDatabase() {
        final ArrayList<ShiftTime> shiftTimes = new ArrayList<>();
        DatabaseReference databaseShiftTimes = database.getReference().child("shiftTime").child(presenter.getCurrentUser().getCompanies().get(0));
        databaseShiftTimes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shiftTime: snapshot.getChildren()
                     ) {
                    shiftTimes.add(shiftTime.getValue(ShiftTime.class));
                }
                presenter.setShiftTimes(shiftTimes);
                notifyDataReady();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Ability to add shifts
    public void addShift(View view){
        Intent intent = new Intent(this, AddShiftActivity.class);
        startActivity(intent);
    }

    public void clearSchedule(View view) {

    }

    public void publishSchedule(View view) {

    }

    // display a list of current shifts that will be added to schedule
    public void setShiftList() {
        LinearLayout shiftList = findViewById(R.id.shift_list);

        // add all shifts to the list
        for (final Shift shift: presenter.getShifts()) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView shiftType = new TextView(this);
            shiftType.setText(shift.getShiftType());
            row.addView(shiftType);

            TextView date = new TextView(this);
            date.setText(shift.getDate().toString());
            row.addView(date);



            Button edit = new Button(this);
            edit.setText("Edit");
            edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String shiftId = shift.getShiftID();
                    editShift(shiftId);
                }
            });
            row.addView(edit);
        }
    }

    public void editShift(String shiftId) {

    }

    @Override
    public void notifyDataReady() {
        setShiftList();
    }

    @Override
    public void notifyNewDataToSave() {

    }
}