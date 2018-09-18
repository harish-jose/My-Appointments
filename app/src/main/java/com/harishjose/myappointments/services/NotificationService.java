package com.harishjose.myappointments.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.controller.MyAppointmentsApplication;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.Calendar;

/**
 * Created by harish.jose on 18-09-2018.
 */
public class NotificationService {

    public static void setNotificationAlarm(Appointment appointment) {
        //Create a new PendingIntent and add it to the AlarmManager
        int requestId = (int) (appointment.getAppointmentId()%10000);
        Calendar triggerCal = GeneralUtil.parseDateTimeToCalendar(appointment.getActivityStartDate());

        if(!GeneralUtil.isUpcomingTime(triggerCal.getTime())){
            return;
        }
        Intent intent = new Intent(MyAppointmentsApplication.getInstance().getApplicationContext(), NotificationAlarmReceiverService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.APPOINTMENT, appointment);
        bundle.putInt("Hello", 121);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getService(MyAppointmentsApplication.getInstance().getApplicationContext(),
                requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);



       /* AlarmManager am =
                (AlarmManager)MyAppointmentsApplication.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        //am.set(AlarmManager.RTC_WAKEUP, triggerCal.getTimeInMillis(), pendingIntent);

        am.setExact(AlarmManager.RTC_WAKEUP, triggerCal.getTimeInMillis(), pendingIntent);*/
    }
}
