package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.google.firebase.auth.FirebaseAuth;
// The SignUp view to create an account
public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private static final int CREATE_COMPANY_REQUEST = 2;
    private String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();

        Intent intent = new Intent(this, AttachCompanyActivity.class);
        startActivityForResult(intent, CREATE_COMPANY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // get company info from AttachCompanyActivity
        Intent attachCompany = getIntent();
        companyName = attachCompany.getStringExtra("companyName");
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

        EditText un = (EditText) findViewById(R.id.email);
        String email = un.getText().toString();

        EditText pass = (EditText) findViewById(R.id.password);
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