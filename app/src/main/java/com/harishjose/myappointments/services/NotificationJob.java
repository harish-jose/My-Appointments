package com.harishjose.myappointments.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.harishjose.myappointments.R;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.controller.MyAppointmentsApplication;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.screens.SplashActivity;
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
        generateNotification(params.getExtras().getString(AppConstants.TITLE, "Your Appointment Within 5 Minutes"),
                params.getExtras().getString(AppConstants.BODY, ""), params.getTag());
        return Result.SUCCESS;
    }

    /**
     * Function to generate the notification
     * @param title
     * @param body
     * @param id
     */
    private void generateNotification(String title, String body, String id) {

        PendingIntent pendingIntent = PendingIntent.getActivity
                (MyAppointmentsApplication.getInstance(), 5005, new Intent(MyAppointmentsApplication.getInstance(), SplashActivity.class)
                        ,PendingIntent.FLAG_CANCEL_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MyAppointmentsApplication.getInstance(), "chanel")
                .setSmallIcon(R.drawable.ic_calendar_clock)
                .setContentTitle(title)
                .setColor(GeneralUtil.getColor(R.color.colorAccent))
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setVibrate(new long[] { 100, 500, 100, 500 })
                .setSound(alarmSound)
                .setContentIntent(pendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 22;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) MyAppointmentsApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(id, "My Appointments", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[] { 100, 500, 100, 500 });
            assert mNotifyMgr != null;
            notificationBuilder.setChannelId(id);
            mNotifyMgr.createNotificationChannel(notificationChannel);
        }
        assert mNotifyMgr != null;
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, notificationBuilder.build());

    }

    /**
     * To set the alarm at specific time
     * @param appointment
     */
    public static void scheduleJob(Appointment appointment) {
        Calendar eventTime = GeneralUtil.parseDateTimeToCalendar(appointment.getActivityStartDate());

        if(!GeneralUtil.isUpcomingTime(eventTime.getTime())){
            return;
        }

        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putString(AppConstants.TITLE, appointment.getSubject());
        String notificationBody = GeneralUtil.formatDateTime(appointment.getActivityStartDate(), AppConstants.hh_mm_am_pm) +
                " - " + GeneralUtil.formatDateTime(appointment.getActivityEndDate(), AppConstants.hh_mm_am_pm) +
                " | " + appointment.getLocation();
        extras.putString(AppConstants.BODY, notificationBody);

        int requestId = (int) (appointment.getAppointmentId()%10000);

        long triggerTime = eventTime.getTimeInMillis() - System.currentTimeMillis() - 300000L;
        try {
            int jobId = new JobRequest.Builder(TAG + requestId)
                    .setExact(triggerTime)
                    .setExtras(extras)
                    .setUpdateCurrent(true)
                    .build()
                    .schedule();
        } catch (Exception e) {

        }
    }
}
