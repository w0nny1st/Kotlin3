package com.example.kotlin3.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "test_results")
data class TestResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val percentage: Double,
    val timestamp: Long = Date().time
)