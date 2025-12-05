package com.example.jobfinder.data

import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.model.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface FavoriteJobRepository {
    fun getFavoriteJobs(): Flow<List<FavoriteJob>>
    fun getFavoriteJobByJobId(jobId: String): Flow<FavoriteJob?>

    suspend fun insertFavoriteJob(favoriteJob: FavoriteJob)
    suspend fun deleteFavoriteJob(jobId: String)

    // Convenience function for UI: marks as favourite / unfavourite
    suspend fun toggleFavorite(job: Job)
}

class OfflineFavoriteJobRepository(
    private val favoriteJobDao: FavoriteJobDao
) : FavoriteJobRepository {

    override fun getFavoriteJobs(): Flow<List<FavoriteJob>> =
        favoriteJobDao.getFavoriteJobs()

    override fun getFavoriteJobByJobId(jobId: String): Flow<FavoriteJob?> =
        favoriteJobDao.getFavoriteJobByJobId(jobId)

    override suspend fun insertFavoriteJob(favoriteJob: FavoriteJob) =
        favoriteJobDao.insertFavoriteJob(favoriteJob)

    override suspend fun deleteFavoriteJob(jobId: String) =
        favoriteJobDao.deleteFavoriteJob(jobId)

    override suspend fun toggleFavorite(job: Job) {
        // Check if this job is already in favorites
        val existing = favoriteJobDao.getFavoriteJobByJobId(job.id).firstOrNull()
        if (existing == null) {
            // Map Job -> FavoriteJob and insert
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
            // Remove from favorites
            favoriteJobDao.deleteFavoriteJob(job.id)
        }
    }
}
