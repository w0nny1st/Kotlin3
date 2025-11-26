package com.example.kotlin3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlin3.data.AppDatabase
import com.example.kotlin3.data.TestResult
import com.example.kotlin3.databinding.ActivityTestBinding
import com.example.kotlin3.repository.TestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding
    private lateinit var repository: TestRepository
    private var userName = ""

    private val questions = listOf(
        "Столица Франции?" to listOf("Лондон", "Берлин", "Париж", "Мадрид"),
        "2 + 2 = ?" to listOf("3", "4", "5", "6"),
        "Самый большой океан?" to listOf("Атлантический", "Индийский", "Тихий", "Северный Ледовитый"),
        "Сколько дней в неделе?" to listOf("5", "6", "7", "8"),
        "Какого цвета снег?" to listOf("Синего", "Зеленого", "Белого", "Красного")
    )

    private var currentQuestion = 0
    private val userAnswers = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("user_name") ?: "Егор"

        val database = AppDatabase.getInstance(this)
        repository = TestRepository(database)

        showQuestion()

        binding.nextButton.setOnClickListener {
            val selectedId = binding.optionsGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val answerIndex = when (selectedId) {
                    binding.option1.id -> 0
                    binding.option2.id -> 1
                    binding.option3.id -> 2
                    binding.option4.id -> 3
                    else -> -1
                }

                userAnswers.add(answerIndex)
                currentQuestion++

                if (currentQuestion < questions.size) {
                    showQuestion()
                } else {
                    saveTestResult()
                }
            } else {
                Toast.makeText(this, "Выберите ответ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showQuestion() {
        val (question, options) = questions[currentQuestion]
        binding.questionTextView.text = "${currentQuestion + 1}. $question"
        binding.option1.text = options[0]
        binding.option2.text = options[1]
        binding.option3.text = options[2]
        binding.option4.text = options[3]

        binding.optionsGroup.clearCheck()

        binding.nextButton.text = if (currentQuestion == questions.size - 1) "Завершить" else "Далее"
    }

    private fun saveTestResult() {
        val correctAnswers = listOf(2, 1, 2, 2, 2)
        var correctCount = 0

        for (i in userAnswers.indices) {
            if (i < correctAnswers.size && userAnswers[i] == correctAnswers[i]) {
                correctCount++
            }
        }

        val totalQuestions = questions.size
        val percentage = (correctCount.toDouble() / totalQuestions) * 100

        val testResult = TestResult(
            userName = userName,
            correctAnswers = correctCount,
            totalQuestions = totalQuestions,
            percentage = percentage
        )

        CoroutineScope(Dispatchers.IO).launch {
            repository.saveResult(testResult)

            runOnUiThread {
                val intent = Intent(this@TestActivity, ResultActivity::class.java)
                intent.putExtra("correct_answers", correctCount)
                intent.putExtra("total_questions", totalQuestions)
                intent.putExtra("user_name", userName)
                startActivity(intent)
                finish()
            }
        }
    }
}