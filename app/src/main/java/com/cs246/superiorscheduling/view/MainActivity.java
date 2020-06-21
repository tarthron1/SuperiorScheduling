package com.cs246.superiorscheduling.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.MainPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
// The first view (Login page) of Superior Scheduling
public class MainActivity extends AppCompatActivity implements Listener {

    MainPresenter presenter = new MainPresenter();
    private FirebaseAuth mAuth;
    private static String TAG = "MainActivity";
    private static final int CREATE_USER_REQUEST = 1;
    private FirebaseDatabase database;

    private EditText loginEmailField;
    private EditText loginPasswordField;

    //temporary hard coded company
    private Company company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.registerListeners(this);
        mAuth = FirebaseAuth.getInstance();
        loginEmailField = (EditText) findViewById(R.id.editTextLoginEmail);
        loginPasswordField = (EditText) findViewById(R.id.editTextTextPassword);
    }

    // Ability to login into your account
    public void login(View view){
        //if manager is true deliver EmployerView
        String email = loginEmailField.getText().toString().trim();
        final String password = loginPasswordField.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, ("Authentication failed. "),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
        //if manager is false deliver EmployeeView
    }

    // Ability to create an account
    public void createAccount(String email, String password, final String firstName, final String lastName, final String nickName, final Date birthDate) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            User modelUser = new User(user.getUid(),firstName, lastName, nickName, birthDate);
                            // todo pass modelUser to a company object once we figure out where to create said company object
                            presenter.setCurrentUser(modelUser);

                            // temporary make user manager at new company
                            company = new Company("Test", modelUser);
                            presenter.addCompany(company);
                            getInstance();

                            notifyNewDataToSave();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    // Creates a new intent to move the user from the MainActivity to the SignUpActivity
    public void signUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, CREATE_USER_REQUEST);
    }

    // Assigns the signUp values to their respective variables
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_USER_REQUEST){
            if (resultCode == RESULT_OK){
                String firstName = data.getStringExtra("firstName");
                String lastName = data.getStringExtra("lastName");
                String nickName = data.getStringExtra("nickName");
                String birthDateString = data.getStringExtra("birthDate");
                Boolean manager = data.getBooleanExtra("manager", false);
                String email = data.getStringExtra("email");
                String password = data.getStringExtra("password");

                //convert birthDateString to Date object
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                Date birthDate;

                try {
                    birthDate = formatter.parse(birthDateString);
                }

                catch (java.text.ParseException e){
                    birthDate = null;
                    e.printStackTrace();
                }
                createAccount(email, password, firstName, lastName, nickName, birthDate);


            }
        }
    }


    @Override
    public void notifyChangeOnCloud() {

    }

    @Override
    public void notifyNewDataToSave() {

    }


        @Override
        protected void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }

    // Updates the User Interface
    private void updateUI(FirebaseUser user) {
        if (user != null){
            //todo check to see if the user is a manager then create the correct activity.
            //create a list of companies that apply pass that list to the intent.
            ArrayList<Company> companiesUserIsManager = new ArrayList<>();
            ArrayList<Company> companiesUserIsEmployee = new ArrayList<>();
            for (Company company: presenter.getCompanies()
                 ) {
                ArrayList<User> managerList = company.getManagerList();
                ArrayList<User> employeeList = company.getActiveEmployeeList();
                if (managerList != null) {
                    if (managerList.contains(presenter.getCurrentUser())) {
                        companiesUserIsManager.add(company);
                    }
                }
                if (employeeList != null) {
                    if (employeeList.contains(presenter.getCurrentUser())) {
                        companiesUserIsEmployee.add(company);
                    }
                }
            }

            if (companiesUserIsManager.size() != 0){
                Intent intent = new Intent(this, EmployerView.class );
                intent.putExtra("uid", presenter.getCurrentUser().getUid());
                startActivity(intent);


            }
        }
    }

    // Gets the correct user and company from the database
    public void getInstance(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference user = database.getReference("users");
        DatabaseReference company = database.getReference("companies");
        user.setValue(presenter.getCurrentUser());
        company.setValue(this.company);

    }



}