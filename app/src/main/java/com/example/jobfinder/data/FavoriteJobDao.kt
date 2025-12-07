package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobfinder.model.FavoriteJob
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteJobDao {

    // Get all saved favorite jobs newest first
    @Query("SELECT * FROM favorite_job ORDER BY id DESC")
    fun getFavoriteJobs(): Flow<List<FavoriteJob>>

    // Add a job to favorites (replaces if it already exists)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteJob(favoriteJob: FavoriteJob)

    // Remove a job from favorites using its job ID
    @Query("DELETE FROM favorite_job WHERE job_id = :jobId")
    suspend fun deleteFavoriteJob(jobId: String)

    // Check if a specific job is already saved as favorite
    @Query("SELECT * FROM favorite_job WHERE job_id = :jobId LIMIT 1")
    fun getFavoriteJobByJobId(jobId: String): Flow<FavoriteJob?>
}
