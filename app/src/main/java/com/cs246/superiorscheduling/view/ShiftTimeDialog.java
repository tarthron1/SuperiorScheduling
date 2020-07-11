package com.cs246.superiorscheduling.view;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.cs246.superiorscheduling.R;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class ShiftTimeDialog extends DialogFragment {
    private TimePicker startPick, endPick;
    private TextView actionOk, actionCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_shift_time, container, false);
        actionCancel = view.findViewById(R.id.shiftTime_cancel);
        actionOk = view.findViewById(R.id.shiftTime_ok);
        startPick = view.findViewById(R.id.add_start_time);
        endPick = view.findViewById(R.id.add_end_time);

        // close dialog when cancel is clicked
        actionCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        // send times to AddEmployeeActivity when ok is clicked
        actionOk.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // convert start TimePicker values to Time
                int startHour = startPick.getHour();
                int startMinute = startPick.getMinute();

                Calendar startCal = Calendar.getInstance();
                startCal.set(Calendar.HOUR, startHour);
                startCal.set(Calendar.MINUTE, startMinute);

                long startMillis = startCal.getTimeInMillis();

                Time startTime = new Time(startMillis);

                // convert end TimePicker values to Time
                int endHour = endPick.getHour();
                int endMinute = endPick.getMinute();

                Calendar endCal = Calendar.getInstance();
                endCal.set(Calendar.HOUR, endHour);
                endCal.set(Calendar.MINUTE, endMinute);

                long endMillis = endCal.getTimeInMillis();

                Time endTime = new Time(endMillis);

                //check if end time is earlier than start time, set it equal to start time
                if (endTime.before(startTime)) {
                    endTime = startTime;
                }

                //call AddEmployeeActivity addShiftTime()
                ((AddEmployeeActivity)getActivity()).addShiftTime(startTime, endTime);

                //close dialog
                getDialog().dismiss();
            }
        });

        return view;
    }
}
