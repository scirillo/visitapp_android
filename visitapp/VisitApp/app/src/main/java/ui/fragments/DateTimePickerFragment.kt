package com.vistapp.visitapp.fragments

import android.annotation.SuppressLint
import android.app.Dialog

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker

import com.vistapp.visitapp.listeners.DateSelectedListener

import java.util.Calendar

/**
 * Created by Santiago Cirillo on 15/05/2017.
 */

@SuppressLint("ValidFragment")
class DateTimePickerFragment(private val listener: DateSelectedListener) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        listener.onDateSet(hourOfDay, minute)
    }
}
