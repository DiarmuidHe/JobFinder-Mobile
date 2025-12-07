package com.example.jobfinder.data

import com.example.jobfinder.model.Job
import kotlinx.coroutines.flow.Flow

interface JobRepository {

    fun getJobsByQuery(query: String): Flow<List<Job>>
    fun getAllJobs(): Flow<List<Job>>

    suspend fun updateApplied(jobId: String, applied: Boolean)   // NEW
}

class OfflineJobRepository(
    private val jobDao: JobDao
) : JobRepository {

    override fun getJobsByQuery(query: String): Flow<List<Job>> =
        if (query.isBlank()) jobDao.getAllJobs()
        else jobDao.getJobsByQuery(query)

    override fun getAllJobs(): Flow<List<Job>> =
        jobDao.getAllJobs()

    // Update applied state
    override suspend fun updateApplied(jobId: String, applied: Boolean) {
        jobDao.updateApplied(jobId, applied)
    }
}
