package com.example.jobfinder.data

import com.example.jobfinder.model.FavoriteJob
import kotlinx.coroutines.flow.Flow

interface FavoriteJobRepository {
    fun getFavoriteJobs(): Flow<List<FavoriteJob>>
    suspend fun insertFavoriteJob(favoriteJob: FavoriteJob)
    suspend fun deleteFavoriteJob(jobId: String)
}

class OfflineFavoriteJobRepository(
    private val favoriteJobDao: FavoriteJobDao
) : FavoriteJobRepository {

    override fun getFavoriteJobs(): Flow<List<FavoriteJob>> =
        favoriteJobDao.getFavoriteJobs()

    override suspend fun insertFavoriteJob(favoriteJob: FavoriteJob) =
        favoriteJobDao.insertFavoriteJob(favoriteJob)

    override suspend fun deleteFavoriteJob(jobId: String) =
        favoriteJobDao.deleteFavoriteJob(jobId)
}
