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

    // DAO for all job-related queries
    abstract fun jobDao(): JobDao

    // DAO for managing favorite jobs
    abstract fun favoriteJobDao(): FavoriteJobDao

    companion object {
        @Volatile
        private var INSTANCE: JobFinderDatabase? = null

        fun getDatabase(context: Context): JobFinderDatabase {
            return INSTANCE ?: synchronized(this) {

                // Build the Room database using a preloaded asset
                Room.databaseBuilder(
                    context.applicationContext,
                    JobFinderDatabase::class.java,
                    "job_finder.db"              // Name of the local DB file
                )
                    .createFromAsset("database/job_finder.db")  // Load initial data from assets
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
