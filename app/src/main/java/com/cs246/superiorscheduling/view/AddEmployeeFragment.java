package com.cs246.superiorscheduling.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cs246.superiorscheduling.R;
// Ability to add an employee from the AccountManagerView
public class AddEmployeeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_employee_fragment, parent, false);
    }
}
