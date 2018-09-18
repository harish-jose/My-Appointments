package com.harishjose.myappointments.screens.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.harishjose.myappointments.utils.PreferenceUtil;


/**
 * Created by harish.jose on 02-09-2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static AppCompatActivity activity;
    private PreferenceUtil preferenceUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceUtil = new PreferenceUtil(this);
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
     * Function which will return the preference util object.
     * @return
     */
    public PreferenceUtil getPreference(){
        return preferenceUtil;
    }


    /**
     * All binding, initial data assign, listener registration should be done here.
     */
    protected abstract void doInit();



}
