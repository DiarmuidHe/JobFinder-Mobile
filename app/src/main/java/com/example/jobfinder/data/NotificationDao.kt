package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jobfinder.model.NotificationEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    // Save a new notification entry into the database
    @Insert
    suspend fun insert(entry: NotificationEntry)

    // Get all notifications, newest first
    @Query("SELECT * FROM notification_entries ORDER BY timestamp DESC")
    fun getAll(): Flow<List<NotificationEntry>>
}