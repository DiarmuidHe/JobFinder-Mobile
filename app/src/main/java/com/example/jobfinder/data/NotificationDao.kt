package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jobfinder.model.NotificationEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert
    suspend fun insert(entry: NotificationEntry)

    @Query("SELECT * FROM notification_entries ORDER BY timestamp DESC")
    fun getAll(): Flow<List<NotificationEntry>>
}