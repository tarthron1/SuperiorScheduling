package com.cs246.superiorscheduling.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.MainPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MainPresenter.Listener {

    MainPresenter presenter = new MainPresenter();
    private FirebaseAuth mAuth;
    private static String TAG = "MainActivity";
    private static final int CREATE_USER_REQUEST = 1;
    private static String email;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.registerUsers(this);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view){
        //if manager is true deliver EmployerView
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
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
        //if manager is false deliver EmployeeView
    }

    public void signUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, CREATE_USER_REQUEST);
    }

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
                createAccount(email, password, firstName, lastName, nickName, birthDate, manager);


            }
        }
    }

    public void createAccount(String email, String password, final String firstName, final String lastName, final String nickName, final Date birthDate, final Boolean manager) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            User modelUser = new User(user.getUid(),firstName, lastName, nickName, birthDate, manager);
                            // todo pass modelUser to a company object once we figure out where to create said company object
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


    private void updateUI(FirebaseUser user) {
        if (user != null){
            //todo check to see if the user is a manager then create the correct activity.

        }
    }



}