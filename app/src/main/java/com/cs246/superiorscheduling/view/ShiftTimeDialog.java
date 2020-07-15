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

import java.util.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;

public class ShiftTimeDialog extends DialogFragment {
    private TimePicker startPick, endPick;
    private TextView actionOk, actionCancel;
    String sMin;
    String sHour;
    String eMin;
    String eHour;

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
                // convert TimePicker values to Date
                String strStartTime = convertTimes(startPick.getHour(), startPick.getMinute());
                String strEndTime = convertTimes(endPick.getHour(), endPick.getMinute());

                //test output
                System.out.println("Start Time ---> " + strStartTime);
                System.out.println("End Time ---> " + strEndTime);

                DateFormat time = new SimpleDateFormat("kk:mm");
                Date startTime = null;
                Date endTime = null;
                try {
                    startTime = time.parse(strStartTime);
                    endTime = time.parse(strEndTime);

                    //test output
                    System.out.println("Date Start Time ----> " + startTime);
                    System.out.println("Date End Time ----> " + endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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

    private String convertTimes(int hour, int minute) {
        String hourString = convertTimeString(hour);
        String minuteString = convertTimeString(minute);
        return hourString + ":" + minuteString;
    }

    private String convertTimeString(int time) {
        if(time < 10) {
           return "0" + time;
        }
        else {
            return String.valueOf(time);
        }
    }

}
