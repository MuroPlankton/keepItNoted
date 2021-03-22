package com.choicely.keepitnoted.app;

import android.app.Application;

import io.realm.Realm;

public class KeepItNotedApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
