package com.cs246.superiorscheduling.view;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Request;
import com.cs246.superiorscheduling.model.Shift;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.RequestTimeOffPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RequestTimeOffActivity extends AppCompatActivity implements Listener {
    private RequestTimeOffPresenter presenter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Spinner dropdown;
    private EditText dateEditText, reasonView;
    private AwesomeValidation awesomeValidation;
    private Request timeOffRequest;
    private String currentShiftId;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_time_off);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        presenter = new RequestTimeOffPresenter(mAuth.getUid(), database, this);
        reasonView = findViewById(R.id.request_reason);
        dateEditText = findViewById(R.id.request_date);
        setDatePickerDialogListener();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.request_date,"(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d",R.string.invalid_date);
        awesomeValidation.addValidation(this,R.id.request_reason, RegexTemplate.NOT_EMPTY,R.string.invalid_reason);
    }

    public void setDatePickerDialogListener() {
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the DatePicker
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                //create DatePicker dialog
                DatePickerDialog startDatePicker;
                startDatePicker = new DatePickerDialog(RequestTimeOffActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String dateString = "" + convertDateString(selectedMonth + 1) + "/" + convertDateString(selectedDay) + "/" + selectedYear;
                        dateEditText.setText(dateString);
                    }
                }, year, month, day);
                startDatePicker.show();
            }
        });
    }

    private String convertDateString(int date) {
        if(date < 10) {
            return "0" + date;
        }
        else {
            return String.valueOf(date);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitValidation(View view) {
        if (awesomeValidation.validate()) {
            submitRequest(view);
        }
        else {
            Toast.makeText(getApplicationContext()
                    ,"Check Validation.",Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitRequest(View view) {
        String date = null;
        String reason = null;

        Spinner spinner = (Spinner) findViewById(R.id.request_shift);

        // get request date
        date = dateEditText.getText().toString();
        Date localDate= null;
        try {
            localDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // get request reason
        reason = reasonView.getText().toString();
        String shiftType = spinner.getSelectedItem().toString();
        for (Shift currentShift: presenter.getShiftList()) {
            if (currentShift.getDate().equals(localDate) && currentShift.getShiftType().equals(shiftType)) {
                currentShiftId = currentShift.getShiftID();
                break;
            }
        }
        if (shiftType.equals("All Day")){
            timeOffRequest = new Request(presenter.getCurrentUser(), localDate, reason);
        } else {
            timeOffRequest = new Request(presenter.getCurrentUser(), localDate, currentShiftId, reason);
        }


        // save request
        presenter.setNewRequest(timeOffRequest);
        presenter.getCurrentUser().addRequest(timeOffRequest);
        notifyNewDataToSave();
        viewSubmittedRequests();
    }

    public void populateSpinner(){
        dropdown = (Spinner) findViewById(R.id.request_shift);
        // set shift list to dropdown
        dropdown.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getShiftType())); 
    }

    public ArrayList<String> getShiftType() {
        ArrayList<String> shiftTypeKeys = new ArrayList<>();
        Shift bogusShift = new Shift();
        presenter.addShiftType(bogusShift, "All Day");
        if (presenter.getShiftList() != null){
            for (Shift shift : presenter.getShiftList()) {
                presenter.addShiftType(shift, shift.getShiftType());
            }
        }
        for (int i = 0; i < presenter.getShiftTypeList().size(); i++
             ) {
            shiftTypeKeys.addAll(presenter.getShiftTypeList().keySet());
        }
        return shiftTypeKeys;
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
        float margin = 10 * density;

        int genWidth = (int) (width - (px * 2) - (margin * 4)) / 3;

        // set general params
        LinearLayout.LayoutParams genParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        genParams.width = genWidth;
        genParams.gravity = Gravity.CENTER_VERTICAL;
        genParams.setMargins(10, 10, 0, 10);
        params.put("general", genParams);

        // set last view params
        LinearLayout.LayoutParams lastParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lastParams.width = genWidth;
        lastParams.gravity = Gravity.CENTER_VERTICAL;
        lastParams.setMargins(10, 10, 10, 10);
        params.put("last", lastParams);

        return params;
    }

    public void viewSubmittedRequests() {
        HashMap<String, LinearLayout.LayoutParams> params = getParams();
        LinearLayout requests = findViewById(R.id.submitted_requests);
        requests.removeAllViews();
        // add submitted requests to the list
        if (presenter.getUserRequests() != null) {
            for (Request request : presenter.getUserRequests()) {
                LinearLayout separator = createRowSeparator();
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);

                // Set date to row
                TextView date = new TextView(this);
                date.setLayoutParams(params.get("general"));
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                date.setText(df.format(request.getDate()));
                row.addView(date);

                // Check if there is a shift for the request
                if (request.getShiftID() != null) {
                    // Set shiftType to row
                    TextView shift = new TextView(this);
                    shift.setLayoutParams(params.get("general"));
                    shift.setText("Shift Type"); //todo: get shift type for submitted requests
                    row.addView(shift);
                }

                // Check if there is a reason for the request
                if (request.getReason() != null) {
                    // Set reason to row
                    TextView reason = new TextView(this);
                    reason.setLayoutParams(params.get("last"));
                    reason.setText(request.getReason());
                    row.addView(reason);
                }
                requests.addView(separator);
                requests.addView(row);
            }
            LinearLayout separator = createRowSeparator();
            requests.addView(separator);
        }
    }

    @Override
    public void notifyDataReady() {
        populateSpinner();
        viewSubmittedRequests();
    }

    @Override
    public void notifyNewDataToSave() {
        presenter.notifyNewDataToSave();
    }
}
