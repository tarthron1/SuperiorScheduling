package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;

import java.util.ArrayList;
import java.util.List;

public class AttachCompanyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_company);

        //populate company spinner options
        final Spinner spinner = (Spinner) findViewById(R.id.company);

        List<String> companies = new ArrayList<>();
        // temporary hard coded company options - need to set up dynamic list.
        companies.add("Company 1");
        companies.add("Company 2");

        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, companies));
    }

    public void createUser(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);

        // get selected company from spinner
        Spinner companies = (Spinner) findViewById(R.id.company);
        String companyName = companies.getSelectedItem().toString();

        // add company to intent
        intent.putExtra("company", companyName);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void createCompany(View view){
        Intent intent = new Intent(this, SignUpActivity.class);

        // get company name from editText
        EditText cn = (EditText) findViewById(R.id.company_name);
        String companyName = cn.getText().toString();

        // add company to intent
        intent.putExtra("company", companyName);

        setResult(RESULT_OK, intent);
        finish();
    }

}
