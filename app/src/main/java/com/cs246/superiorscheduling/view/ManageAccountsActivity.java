package com.cs246.superiorscheduling.view;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.Company;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.ManageAccountsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// The Manager's View of accounts
public class ManageAccountsActivity extends AppCompatActivity implements Listener {

    private static String TAG = "ManageAccountsActivity";
    private ManageAccountsPresenter presenter;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference currentUserDatabaseLocation;
    private DatabaseReference allCompanyDatabaseLocation;
    private DatabaseReference allUsersDatabaseLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        presenter = new ManageAccountsPresenter();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUserDatabaseLocation = database.getReference().child("users").child(mAuth.getCurrentUser().getUid());
        allCompanyDatabaseLocation = database.getReference().child("companies");
        allUsersDatabaseLocation = database.getReference().child("users");
        getCurrentUser();


        int testing = 0;

    }

    private void getCurrentUser() {
        ValueEventListener getCurrentUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.setCurrentUser(snapshot.getValue(User.class));
                getCurrentCompany();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "get user info failed", error.toException());
            }
        };
        currentUserDatabaseLocation.addValueEventListener(getCurrentUserListener);
    }

    private void getCurrentCompany() {
        ValueEventListener getCurrentCompanyListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presenter.setCurrentCompany(snapshot.child(presenter.getCurrentUser().getCompanies().get(0)).getValue(Company.class));
                getEmployeeList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        allCompanyDatabaseLocation.addValueEventListener(getCurrentCompanyListener);
    }

    private void getEmployeeList() {
        ValueEventListener getEmployeeListListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()
                     ) {
                    User tempUser = user.getValue(User.class);
                    if (presenter.getCurrentCompany().getManagerList().contains(tempUser.getUserID())){
                        presenter.addEmployee(tempUser);
                    } else if (presenter.getCurrentCompany().getActiveEmployeeList().contains(tempUser.getUserID())){
                        presenter.addEmployee(tempUser);
                    } else if (presenter.getCurrentCompany().getInactiveEmployeeList().contains(tempUser.getUserID())){
                        presenter.addEmployee(tempUser);
                    }
                }
                notifyDataReady();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        allUsersDatabaseLocation.addValueEventListener(getEmployeeListListener);
    }

    //Any Code that relies on data from the cloud needs to be called from this function
    @Override
    public void notifyDataReady() {

    }

    //Once Data is ready to be saved to the cloud call this function.
    @Override
    public void notifyNewDataToSave() {
        currentUserDatabaseLocation.setValue(presenter.getCurrentUser());
        allCompanyDatabaseLocation.child(presenter.getCurrentCompany().getCompanyID()).setValue(presenter.getCurrentCompany());
    }
}