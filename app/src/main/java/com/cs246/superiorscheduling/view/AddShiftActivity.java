package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;
// Fragment to view the shifts
public class AddShiftActivity extends AppCompatActivity implements Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);
    }

    public void createShift(View view){
        Intent intent = new Intent(this, AddEmployeeActivity.class);

        // send shift type and number needed to AddEmployeeActivity
        EditText shiftEditText = (EditText) findViewById(R.id.shift_type);
        String shiftType = shiftEditText.getText().toString();
        intent.putExtra("shiftType", shiftType);

        EditText numEditText = (EditText) findViewById(R.id.number_needed);
        String numberNeeded = numEditText.getText().toString();
        intent.putExtra("numberNeeded", numberNeeded);

        startActivity(intent);
    }

    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}
