package com.avantrio.assessment.service

import android.app.Application
import android.content.Context
import android.text.format.DateFormat
import com.avantrio.assessment.room.UserDao
import com.avantrio.assessment.room.UserDatabase
import java.util.*
import java.util.concurrent.TimeUnit

class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDatabase = UserDatabase.getInstance(this@CoreApp)
        userDao = appDatabase?.userDao()

        tinyDB = TinyDB(this@CoreApp)
    }

    companion object {
        var instance: CoreApp? = null
            private set

        lateinit var tinyDB: TinyDB
        //Database
        var appDatabase: UserDatabase? = null
        var userDao: UserDao? = null
        fun getContext(): Context? {
            return instance
        }

    }
}