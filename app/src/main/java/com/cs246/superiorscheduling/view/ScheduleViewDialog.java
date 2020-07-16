package com.cs246.superiorscheduling.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cs246.superiorscheduling.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleViewDialog extends DialogFragment {
    private DatePicker startPick;
    private TextView actionOk, actionCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_schedule_view, container, false);
        actionCancel = view.findViewById(R.id.schedule_cancel);
        actionOk = view.findViewById(R.id.schedule_ok);
        startPick = view.findViewById(R.id.schedule_start_date);

        // close dialog when cancel is clicked
        actionCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        // send dates to ScheduleViewActivity when ok is clicked
        actionOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // convert DatePicker values to date
                String strStartDate = convertDates(startPick.getMonth(), startPick.getDayOfMonth(), startPick.getYear());

                DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                Date startDate = null;
                try {
                    startDate = date.parse(strStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //call ScheduleViewActivity setScheduleView()
                ((ScheduleViewActivity)getActivity()).setScheduleView(startDate);

                //close dialog
                getDialog().dismiss();
            }
        });

        return view;
    }

    private String convertDates(int month, int dayOfMonth, int year) {
        return (month + 1) + "/" + dayOfMonth + "/" + year;
    }
}
