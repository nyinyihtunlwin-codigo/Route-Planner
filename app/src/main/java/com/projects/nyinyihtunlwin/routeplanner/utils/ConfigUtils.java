package com.projects.nyinyihtunlwin.routeplanner.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.projects.nyinyihtunlwin.routeplanner.RoutePlannerApp;

public class ConfigUtils {

    private static final String KEY_EMAIL = "KEY_EMAIL";
    private static final String KEY_PASSWORD = "KEY_PASSWORD";

    private static ConfigUtils mObjInstance;

    private SharedPreferences mSharedPreferences;

    private ConfigUtils() {
        mSharedPreferences = RoutePlannerApp.getContext().getSharedPreferences("ConfigUtils", Context.MODE_PRIVATE);
    }

    public static ConfigUtils getInstance() {
        if (mObjInstance == null) {
            mObjInstance = new ConfigUtils();
        }
        return mObjInstance;
    }

    public void saveCurrentUser(String email) {
        mSharedPreferences.edit().putString(KEY_EMAIL, email).apply();
    }

    public String loadCurrentUser() {
        return mSharedPreferences.getString(KEY_EMAIL, "");
    }

}
