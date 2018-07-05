package com.projects.nyinyihtunlwin.routeplanner;

import android.app.Application;
import android.content.Context;

public class RoutePlannerApp extends Application {

    public static final String TAG = RoutePlannerApp.class.getName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

}
