package com.harishjose.myappointments.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.harishjose.myappointments.screens.common.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by harish.jose on 18-09-2018.
 */
public class DateTimePickerUtil {
    private Context context;
    private Calendar date;
    private Calendar minTime;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar currentDate;
    private OnSelectedListener listener;

    public DateTimePickerUtil(Context context, Calendar minTime, Calendar selectedTime, OnSelectedListener listener) {
        this.context = context;
        date = Calendar.getInstance();
        if(minTime != null) {
            this.minTime = (Calendar) minTime.clone();
        } else {
            this.minTime = (Calendar) date.clone();
        }
        this.currentDate = (Calendar) selectedTime.clone();
        this.listener = listener;
    }

    public void showPicker() {
        datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        if (listener != null) {
                            listener.onSelected(date.getTimeInMillis());
                        }
                        Log.v("tag", "Selected date " + date.getTime());
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false);
                if(date.get(Calendar.DAY_OF_YEAR) == minTime.get(Calendar.DAY_OF_YEAR) && date.get(Calendar.YEAR) == minTime.get(Calendar.YEAR)) {
                    timePickerDialog.setMinTime(minTime.get(Calendar.HOUR_OF_DAY), minTime.get(Calendar.MINUTE)+1, minTime.get(Calendar.SECOND));
                }
                timePickerDialog.show(BaseActivity.getActivityInstance().getFragmentManager(), "TimePickerDialog");
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        datePickerDialog.setMinDate(minTime);
        datePickerDialog.show(BaseActivity.getActivityInstance().getFragmentManager(), "DateTimePicker");

    }

    public interface OnSelectedListener {
        void onSelected(long timeInMillis);
    }
}
