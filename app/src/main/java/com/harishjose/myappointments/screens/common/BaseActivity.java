package com.harishjose.myappointments.screens.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by harish.jose on 02-09-2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static AppCompatActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }


    public static AppCompatActivity getActivityInstance() {
        return activity;
    }

    /**
     * All binding, initial data assign, listener registration should be done here.
     */
    protected abstract void doInit();



}
