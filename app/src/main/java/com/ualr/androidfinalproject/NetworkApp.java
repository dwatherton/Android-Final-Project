package com.ualr.androidfinalproject;

import android.app.Application;
import android.content.Context;

public class NetworkApp extends Application {

    private static NetworkApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static NetworkApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

}