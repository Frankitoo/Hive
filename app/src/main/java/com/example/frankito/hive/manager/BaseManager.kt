package com.example.frankito.hive.manager

import android.content.Context
import android.content.SharedPreferences

abstract class BaseManager {

    protected open val dbSharedPreferences = "db_bike_gps"

    protected fun resetSharedPreferences(context: Context) {
        getSharedPreferences(context).edit().clear().apply()
    }

    protected fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(dbSharedPreferences, Context.MODE_PRIVATE)
    }

}