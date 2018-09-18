package com.harishjose.myappointments.services;

import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.harishjose.myappointments.R;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.controller.MyAppointmentsApplication;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.Calendar;

/**
 * Created by harish.jose on 19-09-2018.
 */
public class NotificationJob extends Job {

    public static final String TAG = "Notification_job";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyAppointmentsApplication.getInstance(), "CHANNEL_ID"+ params.getTag())
                .setSmallIcon(R.drawable.ic_calendar_clock)
                .setContentTitle(params.getExtras().getString(AppConstants.TITLE, "Your Appointment Within 5 Minutes"))
                .setContentText(params.getExtras().getString(AppConstants.BODY, ""))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyAppointmentsApplication.getInstance());
        notificationManager.notify(params.getId(), mBuilder.build());
        return Result.SUCCESS;
    }

    public static void scheduleJob(Appointment appointment) {
        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putString(AppConstants.TITLE, appointment.getSubject());
        String notificationBody = GeneralUtil.formatDateTime(appointment.getActivityStartDate(), AppConstants.hh_mm_am_pm) +
                " - " + GeneralUtil.formatDateTime(appointment.getActivityEndDate(), AppConstants.hh_mm_am_pm) +
                " | " + appointment.getLocation();
        extras.putString(AppConstants.BODY, notificationBody);

        int requestId = (int) (appointment.getAppointmentId()%10000);
        Calendar triggerCal = GeneralUtil.parseDateTimeToCalendar(appointment.getActivityStartDate());


        int jobId = new JobRequest.Builder(TAG + requestId)
                .setExecutionWindow(triggerCal.getTimeInMillis()-360000L, triggerCal.getTimeInMillis()-300000L)
                .setExtras(extras)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }
}
