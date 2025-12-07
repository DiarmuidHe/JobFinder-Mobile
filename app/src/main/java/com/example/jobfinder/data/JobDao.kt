package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.jobfinder.model.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    // Search jobs by matching text across title, company, location, description, or skills
    @Query("""
        SELECT * FROM job
        WHERE LOWER(title)       LIKE '%' || LOWER(:query) || '%'
           OR LOWER(company)     LIKE '%' || LOWER(:query) || '%'
           OR LOWER(location)    LIKE '%' || LOWER(:query) || '%'
           OR LOWER(description) LIKE '%' || LOWER(:query) || '%'
           OR LOWER(skills)      LIKE '%' || LOWER(:query) || '%'
        ORDER BY title ASC
    """)
    fun getJobsByQuery(query: String): Flow<List<Job>>

    // Get every job in the database sorted by title
    @Query("SELECT * FROM job ORDER BY title ASC")
    fun getAllJobs(): Flow<List<Job>>

    // Update the 'applied' status for a specific job
    @Query("UPDATE job SET applied = :applied WHERE id = :jobId")
    suspend fun updateApplied(jobId: String, applied: Boolean)

    // Fetch only the jobs the user has applied to
    @Query("SELECT * FROM job WHERE applied = 1 ORDER BY title ASC")
    fun getAppliedJobs(): Flow<List<Job>>
}
