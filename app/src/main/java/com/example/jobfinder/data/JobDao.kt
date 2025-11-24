package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Query
import com.example.jobfinder.model.Job
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Query("""
        SELECT * FROM job
        WHERE LOWER(title)    LIKE '%' || LOWER(:query) || '%'
           OR LOWER(company)  LIKE '%' || LOWER(:query) || '%'
           OR LOWER(location) LIKE '%' || LOWER(:query) || '%'
        ORDER BY title ASC
    """)
    fun getJobsByQuery(query: String): Flow<List<Job>>

    @Query("SELECT * FROM job ORDER BY title ASC")
    fun getAllJobs(): Flow<List<Job>>
}
