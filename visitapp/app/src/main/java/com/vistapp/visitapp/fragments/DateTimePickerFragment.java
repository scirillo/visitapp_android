package com.vistapp.visitapp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.vistapp.visitapp.listeners.DateSelectedListener;

import java.util.Calendar;

/**
 * Created by Santiago Cirillo on 15/05/2017.
 */

@SuppressLint("ValidFragment")
public class DateTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener  {
    private DateSelectedListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        listener.onDateSet(hourOfDay, minute);
    }
    public DateTimePickerFragment(DateSelectedListener listener){
     this.listener = listener;
    }
}
