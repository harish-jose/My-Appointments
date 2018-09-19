package com.harishjose.myappointments.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by harish.jose on 18-09-2018.
 * Class which will manage the shared preference
 */

public class PreferenceUtil {
    public static final class PreferenceKeys{
        public static final String APP_NAME = "app_name";
        public static final String IS_INITIALIZED = "is_initialized";
        public static final String OLD_FEED_GUID = "old_feed_guid";

    }
    private final String PREFERENCE_NAME = "com.harishjose.myappointments";
    private Context context;

    public PreferenceUtil(Context context){
        this.context = context;
    }

    /**
     * Function which will return the shared preference instance.
     * @return
     */
    private SharedPreferences getSharedPreference(){
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Function which will returns the editor instance
     * @return
     */
    private SharedPreferences.Editor getEditor(){
        SharedPreferences.Editor editor = getSharedPreference().edit();
        return editor;
    }
    /**
     * Function which will save the value to the preference
     * @param key
     * @param value
     */
    public void save(String key,Object value){
        SharedPreferences.Editor editor = getEditor();
        if(value instanceof String){
            editor.putString(key, (String) value);
        }
        else if(value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }
        else if(value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }
        else if(value instanceof Float){
            editor.putFloat(key, (Float) value);
        }
        editor.commit();
    }

    /**
     * Function which will return the string value corresponding key from shared preference
     * @param key
     * @return
     */
    public String getStringValue(String key){
        return getSharedPreference().getString(key,null);
    }
    /**
     * Function which will return the integer value corresponding key from shared preference
     * @param key
     * @return
     */
    public int getIntegerValue(String key){
        return getSharedPreference().getInt(key,0);

    }
    /**
     * Function which will return the long value corresponding key from shared preference
     * @param key
     * @return
     */
    public long getLongValue(String key){
        return getSharedPreference().getLong(key,0);
    }
    /**
     * Function which will return the boolean value corresponding key from shared preference
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key){
        return getSharedPreference().getBoolean(key,false);
    }
    /**
     * Function which will return the float value corresponding key from shared preference
     * @param key
     * @return
     */
    public float getFloatValue(String key){
        return getSharedPreference().getFloat(key,0);
    }

    /**
     * Function which will clear the all the preference value.
     */
    public void clearPreference(){
        String appName = getStringValue(PreferenceKeys.APP_NAME);
        getSharedPreference().edit().clear().commit();
        save(PreferenceKeys.APP_NAME, appName);
    }

    /**
     * Function which a remove a key from shared preference.
     * @param key
     */
    public void removeKey(String key){
        SharedPreferences.Editor editor = getEditor();
        editor.remove(key);
        editor.apply();
    }

}
