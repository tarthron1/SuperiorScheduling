package com.cs246.superiorscheduling.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.cs246.superiorscheduling.R;
import com.cs246.superiorscheduling.presenter.Listener;

// The Manager's View of accounts
public class ManageAccountsActivity extends AppCompatActivity implements Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
    }

    @Override
    public void notifyChangeOnCloud() {

    }

    @Override
    public void notifyNewDataToSave() {

    }
}