package com.cs246.superiorscheduling.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cs246.superiorscheduling.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
    }

    // collect info and pass back to main activity
    public void addAccount(View view){
        Intent intent = new Intent(this, MainActivity.class);

        // collect info from view
        EditText fn = (EditText) findViewById(R.id.first_name);
        String firstName = fn.getText().toString();

        EditText ln = (EditText) findViewById(R.id.last_name);
        String lastName = ln.getText().toString();

        EditText nn = (EditText) findViewById(R.id.nick_name);
        String nickName = nn.getText().toString();

        EditText bd = (EditText) findViewById(R.id.birth_date);
        String birthDate = bd.getText().toString();

        Switch pos = findViewById(R.id.manager_switch);
        Boolean manager = pos.isChecked();

        EditText un = (EditText) findViewById(R.id.username);
        String username = un.getText().toString();

        EditText pass = (EditText) findViewById(R.id.password);
        String password = pass.getText().toString();

        // Add info to intent
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("nickName", nickName);
        intent.putExtra("birthDate", birthDate);
        intent.putExtra("manager", manager);
        intent.putExtra("username", username);
        intent.putExtra("password", password);

        startActivity(intent);
    }
}