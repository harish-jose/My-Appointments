package com.harishjose.myappointments.controller;

import android.app.Application;


/**
 * Created by harish.jose on 14-09-2018.
 */
public class MyAppointmentsApplication extends Application {
    private static MyAppointmentsApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static synchronized MyAppointmentsApplication getInstance() {
        if (sInstance == null) {
            sInstance = new MyAppointmentsApplication();
        }
        return sInstance;
    }


}
