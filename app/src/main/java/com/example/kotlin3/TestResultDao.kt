package com.example.kotlin3.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TestResultDao {

    @Query("SELECT * FROM test_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<TestResult>>

    @Query("SELECT * FROM test_results WHERE userName = :userName ORDER BY timestamp DESC")
    fun getResultsByUser(userName: String): Flow<List<TestResult>>

    @Query("SELECT AVG(percentage) FROM test_results WHERE userName = :userName")
    suspend fun getAveragePercentage(userName: String): Double?

    @Insert
    suspend fun insertResult(result: TestResult)

    @Delete
    suspend fun deleteResult(result: TestResult)

    @Query("DELETE FROM test_results")
    suspend fun deleteAllResults()
}