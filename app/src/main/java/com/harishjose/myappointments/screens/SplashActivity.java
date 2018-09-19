package com.harishjose.myappointments.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.services.AppointmentService;
import com.harishjose.myappointments.services.AppointmentServiceImpl;
import com.harishjose.myappointments.services.NotificationJob;
import com.harishjose.myappointments.services.RssPollingJob;
import com.harishjose.myappointments.utils.GeneralUtil;
import com.harishjose.myappointments.utils.PreferenceUtil;

import java.util.ArrayList;

/**
 * Created by harish.jose on 18-09-2018.
 */
public class SplashActivity extends Activity {

    private PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferenceUtil = new PreferenceUtil(this);
        doInit();
    }

    private void doInit() {
        if(!preferenceUtil.getBooleanValue(PreferenceUtil.PreferenceKeys.IS_INITIALIZED)) {
            initializeApp();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToHome();
            }
        }, 2000);
    }

    /**
     * Initializes by copying asset files to internal storage so that read and edit will be possible
     */
    private void initializeApp() {
        String appointmentsData = GeneralUtil.loadJSONFromAsset(AppConstants.APPOINTMENTS_DATA_FILE_NAME);
        String contactsData = GeneralUtil.loadJSONFromAsset(AppConstants.CONTACTS_DATA_FILE_NAME);

        //Writing Appointments data to internal storage so that we can modify the data
        GeneralUtil.writeToFile(appointmentsData, AppConstants.APPOINTMENTS_DATA_FILE_NAME);

        //Writing Contacts data to internal storage
        GeneralUtil.writeToFile(contactsData, AppConstants.CONTACTS_DATA_FILE_NAME);

        preferenceUtil.save(PreferenceUtil.PreferenceKeys.IS_INITIALIZED, true);

        AppointmentService appointmentService = new AppointmentServiceImpl();
        appointmentService.readAppointments(new DataCallback<ArrayList<Appointment>, String>() {
            @Override
            public void onSuccess(final ArrayList<Appointment> appointmentArrayList) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        for (Appointment appointment : appointmentArrayList) {
                            NotificationJob.scheduleJob(appointment);
                        }
                    }
                });
            }

            @Override
            public void onFailure(String errorResponse) {

            }
        });
        RssPollingJob.scheduleJob();
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
