package com.projects.nyinyihtunlwin.routeplanner.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.projects.nyinyihtunlwin.routeplanner.RoutePlannerApp;

public class ConfigUtils {

    private static final String KEY_EMAIL = "KEY_EMAIL";
    private static final String KEY_CASH_AMOUNT = "KEY_CASH_AMOUNT";
    private static final String KEY__USED_CASH_AMOUNT = "KEY_USED_CASH_AMOUNT";
    private static final String KEY_ELOAD_AMOUNT = "KEY_ELOAD_AMOUNT";
    private static final String KEY_USED_ELOAD_AMOUNT = "KEY_USED_ELOAD_AMOUNT";

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

    public void saveCurrentCashAmount(long cashAmount) {
        mSharedPreferences.edit().putLong(KEY_CASH_AMOUNT, cashAmount).apply();
    }

    public Long loadCurrentCashAmount() {
        return mSharedPreferences.getLong(KEY_CASH_AMOUNT, -1);
    }


    public void saveCurrentEloadAmount(long eloadAmount) {
        mSharedPreferences.edit().putLong(KEY_ELOAD_AMOUNT, eloadAmount).apply();
    }

    public Long loadCurrentEloadAmount() {
        return mSharedPreferences.getLong(KEY_ELOAD_AMOUNT, -1);
    }

    public void saveUsedCashAmount(long usedCashAmount) {
        mSharedPreferences.edit().putLong(KEY__USED_CASH_AMOUNT, usedCashAmount).apply();
    }

    public Long loadUsedCashAmount() {
        return mSharedPreferences.getLong(KEY__USED_CASH_AMOUNT, 0);
    }

    public void saveUsedEloadAmount(long usedEloadAmount) {
        mSharedPreferences.edit().putLong(KEY_USED_ELOAD_AMOUNT, usedEloadAmount).apply();
    }

    public Long loadUsedEloadAmount() {
        return mSharedPreferences.getLong(KEY_USED_ELOAD_AMOUNT, 0);
    }
}
