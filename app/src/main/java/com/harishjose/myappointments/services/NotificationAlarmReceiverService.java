package com.harishjose.myappointments.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.utils.GeneralUtil;

/**
 * Created by harish.jose on 18-09-2018.
 */
public class NotificationAlarmReceiverService extends IntentService {
    Appointment appointment;

    public NotificationAlarmReceiverService() {
        super(NotificationAlarmReceiverService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        appointment = (Appointment) intent.getSerializableExtra(AppConstants.APPOINTMENT);

        if(appointment == null) {
            Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
            return;
        }

        String notificationBody = GeneralUtil.formatDateTime(appointment.getActivityStartDate(), AppConstants.hh_mm_am_pm) +
                " - " + GeneralUtil.formatDateTime(appointment.getActivityEndDate(), AppConstants.hh_mm_am_pm) +
                " | " + appointment.getLocation();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID"+ appointment.getAppointmentId())
                .setSmallIcon(R.drawable.ic_calendar_clock)
                .setContentTitle(appointment.getSubject())
                .setContentText(notificationBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify((int) (appointment.getAppointmentId()%1000), mBuilder.build());
    }
}
