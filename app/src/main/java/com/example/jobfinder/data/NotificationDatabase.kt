package com.example.jobfinder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobfinder.model.NotificationEntry
@Database(
    entities = [NotificationEntry::class],
    version = 1,
    exportSchema = false
)
abstract class NotificationDatabase : RoomDatabase() {

    // DAO for accessing notification records
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: NotificationDatabase? = null

        fun getInstance(context: Context): NotificationDatabase {
            return INSTANCE ?: synchronized(this) {

                // Create the Room database for storing notifications
                Room.databaseBuilder(
                    context.applicationContext,
                    NotificationDatabase::class.java,
                    "notifications.db"   // Local database file name
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
