package com.usercompany;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferences {

    private static final String TAG = ApplicationPreferences.class.getSimpleName();

    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mEditor = null;

    private static final String PREFS_FILE = "BringPrefsFile";
    private static final String PREF_NOTIFY_PHONE_NUMBER = "pref.notify.phone.number";

    // GCM
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_OLD_REG_ID = "registration_old_id";

    public static final String PREF_SEARCH_EVENTS_PER_REQUEST = "pref.search.events.per.request";

    public ApplicationPreferences(final Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    public Integer getItem(String prefName) {
        return mSharedPreferences.getInt(prefName,0);
    }

    public void setItem(String key, int value) {
        mEditor = mSharedPreferences.edit();

        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public SharedPreferences getAppPreferences(){
        return mSharedPreferences;
    }



}
