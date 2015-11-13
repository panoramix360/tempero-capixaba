package com.creativityloop.android.temperocapixaba;

import android.app.Application;

import com.orm.SugarContext;

public class TemperoCapixabaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}