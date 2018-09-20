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
import android.util.Log;
import android.util.Xml;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.harishjose.myappointments.R;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.controller.MyAppointmentsApplication;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.RssFeed;
import com.harishjose.myappointments.screens.SplashActivity;
import com.harishjose.myappointments.utils.GeneralUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by harish.jose on 19-09-2018.
 */
public class RssPollingJob extends Job {

    public static final String TAG = "Rss_job";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        RssFeedPollingTask task = new RssFeedPollingTask(MyAppointmentsApplication.getInstance(), new RssFeedPollingTask.OnRssFeedCallback() {
            @Override
            public void onNewFeed(RssFeed rssFeed) {
                generateNotification(rssFeed.getChannelTitle(), rssFeed.getMessage(), rssFeed.getGuid());
            }
        });
        Thread t = new Thread(task);
        t.start();
        return Result.SUCCESS;
    }

    /**
     * Function to generate the notification
     * @param title
     * @param body
     * @param id
     */
    public static void generateNotification(String title, String body, String id) {

        PendingIntent pendingIntent = PendingIntent.getActivity
                (MyAppointmentsApplication.getInstance(), 5005, new Intent(MyAppointmentsApplication.getInstance(), SplashActivity.class)
                        ,PendingIntent.FLAG_CANCEL_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MyAppointmentsApplication.getInstance(), "chanel")
                .setSmallIcon(R.drawable.ic_calendar_clock)
                .setContentTitle(title)
                .setColor(GeneralUtil.getColor(R.color.colorAccent))
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(body).setSummaryText("Feeds"))
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
     * To set the repeating alarm for RSS Feeds
     */
    public static void scheduleJob() {
        int jobId = new JobRequest.Builder(TAG)
                .setPeriodic(JobRequest.MIN_INTERVAL)  // 15 min interval
                .build()
                .schedule();
    }
}
