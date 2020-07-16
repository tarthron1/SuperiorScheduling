package com.cs246.superiorscheduling.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.cs246.superiorscheduling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.utilities.Validation;

import java.util.Calendar;

// The SignUp view to create an account
public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private static final int CREATE_COMPANY_REQUEST = 2;
    private String companyName;
    EditText fn, ln, nn, bd, un, pass, cpass;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();

        Intent intent = new Intent(this, AttachCompanyActivity.class);
        startActivityForResult(intent, CREATE_COMPANY_REQUEST);
        fn = (EditText) findViewById(R.id.first_name);
        ln = (EditText) findViewById(R.id.last_name);
        nn = (EditText) findViewById(R.id.nick_name);
        bd = (EditText) findViewById(R.id.birth_date);
        un = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        cpass = (EditText) findViewById(R.id.cpassword);
        setDatePickerDialogListener();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.first_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.last_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.birth_date,"(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d",R.string.invalid_date);
        awesomeValidation.addValidation(this,R.id.password, ".{8,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.cpassword,R.id.password,R.string.invalid_confirm);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // get company info from AttachCompanyActivity
        companyName = data.getStringExtra("company");
    }

    public void setDatePickerDialogListener() {
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the DatePicker
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                //create DatePicker dialog
                DatePickerDialog startDatePicker;
                startDatePicker = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String dateString = "" + convertDateString(selectedMonth + 1) + "/" + convertDateString(selectedDay) + "/" + selectedYear;
                        bd.setText(dateString);
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

    public void onClick(View view) {
        if (awesomeValidation.validate()) {
            addAccount(view);
        }
        else {
            Toast.makeText(getApplicationContext()
                    ,"Check Validation.",Toast.LENGTH_SHORT).show();
        }
    }

    // collect info and pass back to main activity
    public void addAccount(View view){
        Intent intent = new Intent(this, MainActivity.class);

        // collect info from view
        String firstName = fn.getText().toString();


        String lastName = ln.getText().toString();


        String nickName = nn.getText().toString();


        String birthDate = bd.getText().toString();

        String email = un.getText().toString();

        String password = pass.getText().toString();

        // Add info to intent
        intent.putExtra("companyName", companyName);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("nickName", nickName);
        intent.putExtra("birthDate", birthDate);
        intent.putExtra("email", email);
        intent.putExtra("password", password);

        setResult(RESULT_OK, intent);
        finish();
    }
}