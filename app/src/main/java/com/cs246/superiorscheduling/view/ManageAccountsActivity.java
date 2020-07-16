package com.cs246.superiorscheduling.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.model.User;
import com.cs246.superiorscheduling.presenter.Listener;
import com.cs246.superiorscheduling.presenter.ManageAccountsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

// The Manager's View of accounts
public class ManageAccountsActivity extends AppCompatActivity implements Listener {

    private static String TAG = "ManageAccountsActivity";
    private ManageAccountsPresenter presenter;
    private LinearLayout table;
    HashMap<String, LinearLayout.LayoutParams> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        presenter = new ManageAccountsPresenter(mAuth.getUid(), database, this);
        getParams();
    }

    //Any Code that relies on data from the cloud needs to be called from this function
    @Override
    public void notifyDataReady() {
        setTableData();
    }

    public LinearLayout createRowSeparator() {
        LinearLayout separator = new LinearLayout(this);
        LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        separator.setLayoutParams(sepParams);
        separator.setBackgroundColor(Color.BLACK);

        return separator;
    }

    // set up layout parameters
    public void getParams() {
        params = new HashMap<>();

        // calculate name view width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        float density = this.getResources().getDisplayMetrics().density;
        float px = 16 * density;

        int nameWidth = (int) (width - (px * 2)) / 2;

        // calculate other view widths
        int genWidth = (width - nameWidth) / 2;

        // set general params
        LinearLayout.LayoutParams genParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        genParams.width = genWidth;
        genParams.gravity = Gravity.CENTER;
        params.put("general", genParams);

        // set name params
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.width = nameWidth;
        nameParams.gravity = Gravity.CENTER;
        nameParams.setMargins(10, 20, 0, 20);
        params.put("name", nameParams);

        // set position params
        LinearLayout.LayoutParams posParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        posParams.width = genWidth - 150;
        posParams.gravity = Gravity.CENTER;
        posParams.setMargins(0, 20, 0, 20);
        params.put("position", posParams);
    }

    public void createTable() {
        // set table header
        LinearLayout th = new LinearLayout(this);

        TextView thName = new TextView(this);
        thName.setLayoutParams(params.get("name"));
        thName.setText("Employee Name");
        thName.setTextSize(18);
        thName.setTypeface(null, Typeface.BOLD);
        th.addView(thName);

        TextView thPos = new TextView(this);
        thPos.setLayoutParams(params.get("general"));
        thPos.setText("Manager");
        thPos.setTextSize(18);
        thPos.setTypeface(null, Typeface.BOLD);
        th.addView(thPos);

        TextView thActive = new TextView(this);
        thActive.setLayoutParams(params.get("general"));
        thActive.setText("Active");
        thActive.setTextSize(18);
        thActive.setTypeface(null, Typeface.BOLD);
        th.addView(thActive);

        LinearLayout separator = new LinearLayout(this);
        LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        separator.setLayoutParams(sepParams);
        separator.setBackgroundColor(Color.BLACK);

        table.addView(th);
        table.addView(separator);
    }

    public void setTableData() {
        table = findViewById(R.id.EmployeeAccounts);
        table.removeAllViews();
        createTable();
        // set employee data onto table
        for (User employee : presenter.getEmployeeList()) {
            LinearLayout separator = createRowSeparator();
            LinearLayout row = new LinearLayout(this);

            // set employee name to row
            TextView name = new TextView(this);
            name.setLayoutParams(params.get("name"));
            name.setTextSize(16);
            name.setText((employee.getFirstName() + " " + employee.getLastName()));
            row.addView(name);

            // set manager switch to row
            Switch manager = new Switch(this);
            manager.setLayoutParams(params.get("position"));
            if (presenter.getCurrentCompany().getManagerList().contains(employee.getUserID())) {
                manager.setChecked(true);
            } else {
                manager.setChecked(false);
            }
            row.addView(manager);

            // set active switch to row
            Switch active = new Switch(this);
            active.setLayoutParams(params.get("general"));
            if (presenter.getCurrentCompany().getActiveEmployeeList().contains(employee.getUserID())) {
                active.setChecked(true);
            } else {
                active.setChecked(false);
            }
            row.addView(active);

            // add row to table
            table.addView(separator);
            table.addView(row);

        }
        LinearLayout separator = createRowSeparator();
        table.addView(separator);
    }

    public void saveChanges(View view) {
        int tableIterator = 3;
        for (User employee : presenter.getEmployeeList()) {
            LinearLayout row = (LinearLayout) table.getChildAt(tableIterator);
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
            tableIterator+=2;
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