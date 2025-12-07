package com.example.jobfinder.data

import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.model.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface FavoriteJobRepository {

    // Stream of all saved favorite jobs
    fun getFavoriteJobs(): Flow<List<FavoriteJob>>


    // Save a job as favorite
    suspend fun insertFavoriteJob(favoriteJob: FavoriteJob)

    // Remove a job from favorites by ID
    suspend fun deleteFavoriteJob(jobId: String)

}

class OfflineFavoriteJobRepository(
    private val favoriteJobDao: FavoriteJobDao
) : FavoriteJobRepository {

    // Return all favorites from local DB
    override fun getFavoriteJobs(): Flow<List<FavoriteJob>> =
        favoriteJobDao.getFavoriteJobs()

    // Look up a single favorite job

    // Insert a new favorite
    override suspend fun insertFavoriteJob(favoriteJob: FavoriteJob) =
        favoriteJobDao.insertFavoriteJob(favoriteJob)

    // Delete a favorite by job ID
    override suspend fun deleteFavoriteJob(jobId: String) =
        favoriteJobDao.deleteFavoriteJob(jobId)


}
