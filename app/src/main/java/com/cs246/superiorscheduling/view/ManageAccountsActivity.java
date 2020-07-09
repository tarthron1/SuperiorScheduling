package com.cs246.superiorscheduling.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

// The Manager's View of accounts
public class ManageAccountsActivity extends AppCompatActivity implements Listener {

    private static String TAG = "ManageAccountsActivity";
    private ManageAccountsPresenter presenter;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        presenter = new ManageAccountsPresenter(mAuth.getUid(), database, this);
    }





    //Any Code that relies on data from the cloud needs to be called from this function
    @Override
    public void notifyDataReady() {
        setTableData();
    }

    public void createTable() {
        // set table header
        TableRow th = new TableRow(this);

        TextView thName = new TextView(this);
        thName.setText("Employee Name");
        thName.setTypeface(null, Typeface.BOLD);
        th.addView(thName);

        TextView thPos = new TextView(this);
        thPos.setText("Manager");
        thPos.setTypeface(null, Typeface.BOLD);
        th.addView(thPos);

        TextView thActive = new TextView(this);
        thActive.setText("Active");
        thActive.setTypeface(null, Typeface.BOLD);
        th.addView(thActive);

        table.addView(th);
    }

    public void setTableData() {
        table = findViewById(R.id.EmployeeAccounts);
        table.removeAllViews();
        createTable();
        // set employee data onto table
        for (User employee : presenter.getEmployeeList()) {
            TableRow row = new TableRow(this);

            // set employee name to row
            TextView name = new TextView(getApplicationContext());
            name.setText((employee.getFirstName() + " " + employee.getLastName()));
            row.addView(name);

            // set manager switch to row
            Switch manager = new Switch(getApplicationContext());
            if (presenter.getCurrentCompany().getManagerList().contains(employee.getUserID())) {
                manager.setChecked(true);
            } else {
                manager.setChecked(false);
            }
            row.addView(manager);

            // set active switch to row
            Switch active = new Switch(getApplicationContext());
            if (presenter.getCurrentCompany().getActiveEmployeeList().contains(employee.getUserID())) {
                active.setChecked(true);
            } else {
                active.setChecked(false);
            }
            row.addView(active);

            // add row to table
            table.addView(row);
        }
    }

    public void saveChanges(View view) {
        int tableIterator = 1;
        for (User employee : presenter.getEmployeeList()) {
            TableRow row = (TableRow) table.getChildAt(tableIterator);
            View manager = row.getChildAt(1);
            View active = row.getChildAt(2);
            if (((Switch)manager).isChecked()) {
                if (!presenter.getCurrentCompany().getManagerList().contains(employee.getUserID())) {
                    presenter.getCurrentCompany().addManager(employee);
                }
            } else {
                if (presenter.getCurrentCompany().getManagerList().size() > 1){
                    presenter.getCurrentCompany().removeManager(employee);
                }
            }
            if (((Switch)active).isChecked()){
                if (!presenter.getCurrentCompany().getActiveEmployeeList().contains(employee.getUserID())) {
                    presenter.getCurrentCompany().toggleActiveEmployee(employee);
                }
            }
            else {
                if (!presenter.getCurrentCompany().getInactiveEmployeeList().contains(employee.getUserID())) {
                    presenter.getCurrentCompany().toggleActiveEmployee(employee);
                }
            }
            tableIterator++;
        }

        // save updated lists
        notifyNewDataToSave();

        // update table
        setTableData();
        Toast.makeText(this, ("Changes Saved."),
                Toast.LENGTH_SHORT).show();
    }

    //Once Data is ready to be saved to the cloud call this function.
    @Override
    public void notifyNewDataToSave() {
        presenter.notifyNewDataToSave();
    }
}