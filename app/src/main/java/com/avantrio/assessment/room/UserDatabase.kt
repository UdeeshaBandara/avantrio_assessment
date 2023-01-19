package com.avantrio.assessment.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.avantrio.assessment.model.UserItem

@Database(entities = [UserItem::class], exportSchema = false, version = 1)
abstract class UserDatabase : RoomDatabase() {


    abstract fun userDao(): UserDao


    companion object {
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): UserDatabase? {
            if (instance == null) {

                synchronized(UserDatabase::class) {
                    instance = Room.databaseBuilder(
                        ctx.applicationContext, UserDatabase::class.java,
                        "avantrio_users"
                    ).fallbackToDestructiveMigration()

                        .build()
                }
            }
            return instance

        }


    }

}