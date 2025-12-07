package com.example.jobfinder.data

import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.model.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface FavoriteJobRepository {

    // Stream of all saved favorite jobs
    fun getFavoriteJobs(): Flow<List<FavoriteJob>>

    // Get a specific favorite job by its job ID
    fun getFavoriteJobByJobId(jobId: String): Flow<FavoriteJob?>

    // Save a job as favorite
    suspend fun insertFavoriteJob(favoriteJob: FavoriteJob)

    // Remove a job from favorites by ID
    suspend fun deleteFavoriteJob(jobId: String)

    // Helper for UI: adds or removes a job from favorites
    suspend fun toggleFavorite(job: Job)
}

class OfflineFavoriteJobRepository(
    private val favoriteJobDao: FavoriteJobDao
) : FavoriteJobRepository {

    // Return all favorites from local DB
    override fun getFavoriteJobs(): Flow<List<FavoriteJob>> =
        favoriteJobDao.getFavoriteJobs()

    // Look up a single favorite job
    override fun getFavoriteJobByJobId(jobId: String): Flow<FavoriteJob?> =
        favoriteJobDao.getFavoriteJobByJobId(jobId)

    // Insert a new favorite
    override suspend fun insertFavoriteJob(favoriteJob: FavoriteJob) =
        favoriteJobDao.insertFavoriteJob(favoriteJob)

    // Delete a favorite by job ID
    override suspend fun deleteFavoriteJob(jobId: String) =
        favoriteJobDao.deleteFavoriteJob(jobId)

    override suspend fun toggleFavorite(job: Job) {
        // Check if this job is already saved
        val existing = favoriteJobDao.getFavoriteJobByJobId(job.id).firstOrNull()

        if (existing == null) {
            // Convert Job → FavoriteJob and save it
            val favorite = FavoriteJob(
                jobId = job.id,
                title = job.title,
                company = job.company,
                location = job.location,
                description = job.description,
                skills = job.skills,
                image = job.image
            )
            favoriteJobDao.insertFavoriteJob(favorite)
        } else {
            // If it exists, remove it from favorites
            favoriteJobDao.deleteFavoriteJob(job.id)
        }
    }
}
