package com.cs246.superiorscheduling.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
// The first view (Login page) of Superior Scheduling
public class MainActivity extends AppCompatActivity implements Listener {

    MainPresenter presenter;
    private FirebaseAuth mAuth;
    private static String TAG = "MainActivity";
    private static final int CREATE_USER_REQUEST = 1;
    private FirebaseDatabase database;
    private DatabaseReference userReference;

    private EditText loginEmailField;
    private EditText loginPasswordField;

    AwesomeValidation awesomeValidation;
    EditText email, password;

    boolean companyExistsInDatabase = false;
    private Company company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        database = FirebaseDatabase.getInstance();
        presenter = new MainPresenter(database, this);

        presenter.registerListeners(this);
        database = FirebaseDatabase.getInstance();
        loginEmailField = findViewById(R.id.editTextLoginEmail);
        loginPasswordField = findViewById(R.id.editTextTextPassword);
        email = (EditText) findViewById(R.id.editTextLoginEmail);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.editTextLoginEmail, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.editTextTextPassword, RegexTemplate.NOT_EMPTY,R.string.lacking_password);
    }

    public void onLogin(View view) {
        if (awesomeValidation.validate()) {
            login(view);
        }
        else {
            Toast.makeText(getApplicationContext()
                    ,"Check Validation.",Toast.LENGTH_SHORT).show();
        }
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
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            presenter.updateMAuth(mAuth.getUid());
                            for (User user: presenter.getAllUsers()
                                 ) {
                                if (user.getUserID().equals(firebaseUser.getUid())){
                                    presenter.setCurrentUser(user);
                                    break;
                                }
                            }
                            updateUI(firebaseUser);
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

   /* public void login(View view){
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
                            final FirebaseUser user = mAuth.getCurrentUser();
                            userReference = database.getReference().child("users").child(user.getUid());
                            ValueEventListener userListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User userFromDatabase;
                                    userFromDatabase = dataSnapshot.getValue(User.class);
                                    Log.d(TAG, userReference.toString());
                                    presenter.setCurrentUser(userFromDatabase);
                                    Log.d(TAG, presenter.getCurrentUser().getUserID());
                                    updateUI(user);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.w(TAG, "get user info failed", databaseError.toException());
                                }
                            };
                            userReference.addValueEventListener(userListener);

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
    }*/


    // Ability to create an account
    public void createAccount(final String companyName, String email, String password, final String firstName, final String lastName, final String nickName, final Date birthDate) {


        if (presenter.getCompanies() == null || presenter.getAllUsers() == null){
            createAccount(companyName, email, password, firstName, lastName, nickName, birthDate);
        } else {
            for (Company company : presenter.getCompanies()
            ) {
                if (company.getName().equals(companyName)) {
                    companyExistsInDatabase = true;
                    break;
                }
            }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                presenter.updateMAuth(mAuth.getUid());
                                User modelUser = new User(user.getUid(), firstName, lastName, nickName, birthDate);
                                // todo pass modelUser to a company object once we figure out where to create said company object
                                presenter.setCurrentUser(modelUser);

                                if (companyExistsInDatabase) {
                                    Company companyToAddNewUserTo = presenter.getCompanyByName(companyName);
                                    companyToAddNewUserTo.addEmployee(modelUser);
                                    modelUser.addCompany(companyToAddNewUserTo);
                                    company = companyToAddNewUserTo;
                                } else {
                                    company = new Company(companyName, modelUser);
                                    presenter.addCompany(company);
                                    modelUser.addCompany(company);
                                }
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
                String companyName = data.getStringExtra("companyName");
                String firstName = data.getStringExtra("firstName");
                String lastName = data.getStringExtra("lastName");
                String nickName = data.getStringExtra("nickName");
                String birthDateString = data.getStringExtra("birthDate");
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
                createAccount(companyName, email, password, firstName, lastName, nickName, birthDate);


            }
        }
    }


    @Override
    public void notifyDataReady() {

    }

    @Override
    public void notifyNewDataToSave() {
            presenter.notifyNewDataToSave();
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
            //create a list of companies that apply pass that list to the intent.
            ArrayList<Company> companiesUserIsManager = new ArrayList<>();
            ArrayList<Company> companiesUserIsEmployee = new ArrayList<>();
            if (presenter.getCurrentUser() != null) {
                for (Company company : presenter.getCompanies()
                ) {
                    ArrayList<String> managerList = company.getManagerList();
                    ArrayList<String> employeeList = company.getActiveEmployeeList();
                    if (managerList != null) {
                        for (String manager : managerList) {
                            if (manager.equals(presenter.getCurrentUser().getUserID())) {
                                companiesUserIsManager.add(company);
                            }
                        }
                    }
                    if (employeeList != null) {
                        for (String employee : employeeList) {
                            if (employee.equals(presenter.getCurrentUser().getUserID())) {
                                companiesUserIsEmployee.add(company);
                            }
                        }
                    }
                }
            }

            if (companiesUserIsManager.size() != 0){
                Intent intent = new Intent(this, EmployerView.class );
                intent.putExtra("uid", presenter.getCurrentUser().getUserID());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, EmployeeView.class);
                startActivity(intent);
            }
        }
    }

    // Old method to saving data to the cloud
//    public void saveDataToCloud(){
//        database = FirebaseDatabase.getInstance();
//        DatabaseReference user = database.getReference("users").child(presenter.getCurrentUser().getUid());
//        DatabaseReference company = database.getReference("companies").child(this.company.getName());
//        user.setValue(presenter.getCurrentUser());
//        company.setValue(this.company);
//
//    }





}