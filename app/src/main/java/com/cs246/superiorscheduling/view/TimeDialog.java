package com.cs246.superiorscheduling.view;

import android.app.TimePickerDialog;
import android.content.Context;

public class TimeDialog  extends TimePickerDialog {
    public TimeDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }
}
