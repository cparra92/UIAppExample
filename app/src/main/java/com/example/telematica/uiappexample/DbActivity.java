package com.example.telematica.uiappexample;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by cristian on 16-11-2016.
 */

public class DbActivity extends Application {

    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        realm.init(this);
        RealmConfiguration config2 = new RealmConfiguration.Builder()
                .name("FirstDB.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

    }
}
