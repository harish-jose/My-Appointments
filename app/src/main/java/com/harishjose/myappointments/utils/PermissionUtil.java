package com.harishjose.myappointments.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * Created by harish.jose on 17-09-2018.
 */
public class PermissionUtil {

    public static final int CALL_REQUEST = 1022;

    public static boolean checkRequestPhoneCall(Activity activity) {
        try
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);
                    return false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
