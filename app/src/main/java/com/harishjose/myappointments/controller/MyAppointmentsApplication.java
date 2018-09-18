package com.harishjose.myappointments.controller;

import android.app.Application;

import com.evernote.android.job.JobManager;


/**
 * Created by harish.jose on 14-09-2018.
 */
public class MyAppointmentsApplication extends Application {
    private static MyAppointmentsApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new JobCreator());
        sInstance = this;
    }

    public static synchronized MyAppointmentsApplication getInstance() {
        if (sInstance == null) {
            sInstance = new MyAppointmentsApplication();
        }
        return sInstance;
    }


}
