package com.creativityloop.android.temperocapixaba;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class TemperoCapixabaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}