package com.example.kotlin3.repository

import com.example.kotlin3.data.AppDatabase
import com.example.kotlin3.data.TestResult
import kotlinx.coroutines.flow.Flow

class TestRepository(private val database: AppDatabase) {

    fun getAllResults(): Flow<List<TestResult>> {
        return database.testResultDao().getAllResults()
    }

    fun getResultsByUser(userName: String): Flow<List<TestResult>> {
        return database.testResultDao().getResultsByUser(userName)
    }

    suspend fun getAveragePercentage(userName: String): Double? {
        return database.testResultDao().getAveragePercentage(userName)
    }

    suspend fun saveResult(result: TestResult) {
        database.testResultDao().insertResult(result)
    }

    suspend fun deleteResult(result: TestResult) {
        database.testResultDao().deleteResult(result)
    }

    suspend fun deleteAllResults() {
        database.testResultDao().deleteAllResults()
    }
}