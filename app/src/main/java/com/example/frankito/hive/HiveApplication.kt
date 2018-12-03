package com.example.frankito.hive

import android.app.Application
import io.realm.RealmConfiguration

class HiveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        io.realm.Realm.init(applicationContext)

        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("myHiveRealm.realm")
                .build()
        io.realm.Realm.setDefaultConfiguration(config)

    }
}