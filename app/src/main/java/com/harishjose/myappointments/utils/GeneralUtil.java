package com.harishjose.myappointments.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.controller.MyAppointmentsApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by harish.jose on 14-09-18.
 * Includes general utils.
 */
public final class GeneralUtil {

    private static SimpleDateFormat serverDateFormat = new SimpleDateFormat(AppConstants.DATETIMEFORMAT);
    /**
     * The screen tag.
     */
    private static String TAG = GeneralUtil.class.getSimpleName();

    /**
     * Gets the string from resource id
     *
     * @param resourceId the resource id
     * @return the string
     */
    public static String getString(int resourceId) {
        return MyAppointmentsApplication.getInstance().getResources().getString(resourceId);
    }

    /**
     * Shows a toast
     * @param message the message
     */
    public static void showShortToast(Context context,String  message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * Shows a toast
     * @param messageResourceId the message resource Id
     */
    public static void showShortToast(Context context,int messageResourceId) {
        Toast.makeText(context, getString(messageResourceId), Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a toast
     * @param message the message
     */
    public static void showLongToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    /**
     * Shows a toast
     * @param messageResourceId the message resource Id
     */
    public static void showLongToast(Context context,int messageResourceId) {
        Toast.makeText(context, getString(messageResourceId), Toast.LENGTH_LONG).show();
    }

    /**
     * Method to check network is present or not.
     *
     * @return true or false - network
     */
    public static boolean isConnectedToNetwork(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }

            }
            return false;
        } catch (Exception ex) {
            Log.d(TAG, TAG + " Exception in isConnectedToNetwork() " + ex.toString());
            return false;
        }
    }
    /**
     * To get the color from color resource
     *
     * @param colorResourceId the color resource id
     * @return the color
     */
    public static int getColor(int colorResourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return MyAppointmentsApplication.getInstance().getResources().getColor(colorResourceId, null);
        }
        return MyAppointmentsApplication.getInstance().getResources().getColor(colorResourceId);
    }

    /**
     * To get the drawable from drawable resource
     *
     * @param drawableResourceId the drawable resource id
     * @return the drawable
     */
    public static Drawable getDrawable(int drawableResourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return MyAppointmentsApplication.getInstance().getResources().getDrawable(drawableResourceId, null);
        }
        return MyAppointmentsApplication.getInstance().getResources().getDrawable(drawableResourceId);
    }
    /**
     * Function which hide keyboard
     * @param view
     */
    public static void hideKeyboard(View view){
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * Function which close soft key board.
     *
     * @param activity the activity.
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * Function which open soft key board.
     *
     * @param activity the activity.
     */
    public static void openKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    /**
     * Function which return app version code
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context){
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Function which return app version name.
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context){
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Email validation
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * check network availablity
     *
     * @return
     */
    public static boolean checkNetworkAvailability() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyAppointmentsApplication.getInstance().getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getInitials(String fullName) {
        String[] names = fullName.split(" ", 2);
        String initials = "";
        try {
            initials += names[0].charAt(0);
            initials += names[1].charAt(0);
        } catch (Exception e) {

        }
        return initials;
    }

    /**
     * To read data from file in assets folder
     * @return
     */
    public static String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = MyAppointmentsApplication.getInstance().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Write data to file in internal storage
     * @param data
     * @param fileName
     */
    public static void writeToFile(String data, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MyAppointmentsApplication.getInstance().openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Read data from file in internal storage
     * @param fileName
     * @return data
     */
    public static String readFromFile(String fileName) {

        String data = "";
        try {
            InputStream inputStream = MyAppointmentsApplication.getInstance().openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                data = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("GeneralUtils", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("GeneralUtils", "Can not read file: " + e.toString());
        }
        return data;
    }

    public static Calendar parseDateTimeToCalendar(String dateTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(serverDateFormat.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static Date parseDateTimeToDate(String dateTime) {
        try {
            return serverDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDateTime(long millis, String reqFormat) {
        SimpleDateFormat reqSdf = new SimpleDateFormat(reqFormat);
        return reqSdf.format(new Date(millis));
    }

    public static String formatDateTime(String dateTime, String reqFormat) {
        SimpleDateFormat reqSdf = new SimpleDateFormat(reqFormat);
        try {
            return reqSdf.format(serverDateFormat.parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDateTime(Date dateTime, String reqFormat) {
        SimpleDateFormat reqSdf = new SimpleDateFormat(reqFormat);
        try {
            return reqSdf.format(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkForSameDay(String dateTime1, String dateTime2) {
        try {
            Date date1 = serverDateFormat.parse(dateTime1);
            Date date2 = serverDateFormat.parse(dateTime2);

            if(date1.getDate() == date2.getDate() && date1.getMonth() == date2.getMonth() && date1.getYear() == date2.getYear()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTomorrow(long milliSeconds) {
        return DateUtils.isToday(milliSeconds - DateUtils.DAY_IN_MILLIS);
    }

    public static boolean isToday(long milliSeconds) {
        return DateUtils.isToday(milliSeconds);
    }

    public static boolean isTimeSlotNow(Date startDate, Date endDate) {
        Date now = new Date();
        return now.after(startDate) && now.before(endDate);
    }

    public static boolean isUpcomingTime(Date startDate) {
        Date now = new Date();
        return startDate.after(now);
    }

    public static boolean isPastTime(Date endDate) {
        Date now = new Date();
        return endDate.before(now);
    }


}
