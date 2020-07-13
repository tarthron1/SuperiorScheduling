package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttachCompanyActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private ArrayList<Company> companies = new ArrayList<>();
    private List<String> companyNames = new ArrayList<>();
    AwesomeValidation awesomeValidation;
    private Spinner spinner;
    EditText cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_company);

        database = FirebaseDatabase.getInstance();

        //populate company spinner options
        getCompanies();
        spinner = (Spinner) findViewById(R.id.company);
        cn = (EditText) findViewById(R.id.company_name);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.company_name, RegexTemplate.NOT_EMPTY,R.string.invalid_cname);
        awesomeValidation.addValidation(this,R.id.first_name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
    }

    public void createUser(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);

        // get selected company from spinner
        Spinner companyNames = (Spinner) findViewById(R.id.company);
        String companyName = companyNames.getSelectedItem().toString();

        // add company to intent
        intent.putExtra("company", companyName);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void getCompanies(){
        DatabaseReference companiesLocation = database.getInstance().getReference().child("companies");
        ValueEventListener companyListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot company: snapshot.getChildren()
                ) {
                    companies.add(company.getValue(Company.class));
                    int useless = 1+1;
                    for (Company c: companies
                    ) {
                        if (!companyNames.contains(c.getName())){
                            companyNames.add(c.getName());
                        }
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, companyNames));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        companiesLocation.addValueEventListener(companyListener);
    }

    public void onClick(View view) {
        if (awesomeValidation.validate()) {
            createCompany(view);
        }
        else
        {
            Toast.makeText(getApplicationContext()
                    ,"Check Validation.",Toast.LENGTH_SHORT).show();
        }
    }

    public void createCompany(View view){
        Intent intent = new Intent(this, SignUpActivity.class);

        // get company name from editText

        String companyName = cn.getText().toString();

        // add company to intent
        intent.putExtra("company", companyName);

        setResult(RESULT_OK, intent);
        finish();
    }

}
