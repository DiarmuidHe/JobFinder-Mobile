package com.example.jobfinder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobfinder.model.Job
import com.example.jobfinder.model.FavoriteJob

@Database(
    entities = [Job::class, FavoriteJob::class],
    version = 1,
    exportSchema = true
)
abstract class JobFinderDatabase : RoomDatabase() {

    abstract fun jobDao(): JobDao
    abstract fun favoriteJobDao(): FavoriteJobDao

    companion object {
        @Volatile
        private var INSTANCE: JobFinderDatabase? = null

        fun getDatabase(context: Context): JobFinderDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    JobFinderDatabase::class.java,
                    "job_finder.db"              // DB file name in /data/data
                )
                    .createFromAsset("database/job_finder.db")  // asset path
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}