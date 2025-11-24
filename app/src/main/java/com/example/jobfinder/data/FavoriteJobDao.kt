package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobfinder.model.FavoriteJob
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteJobDao {

    @Query("SELECT * FROM favorite_job")
    fun getFavoriteJobs(): Flow<List<FavoriteJob>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteJob(favoriteJob: FavoriteJob)

    @Query("DELETE FROM favorite_job WHERE jobId = :jobId")
    suspend fun deleteFavoriteJob(jobId: String)
}
