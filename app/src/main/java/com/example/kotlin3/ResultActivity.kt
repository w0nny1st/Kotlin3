package com.example.kotlin3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin3.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val correctAnswers = listOf(2, 1, 2, 2, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userAnswers = intent.getIntegerArrayListExtra("answers")

        if (userAnswers != null) {
            var correctCount = 0
            for (i in userAnswers.indices) {
                if (i < correctAnswers.size && userAnswers[i] == correctAnswers[i]) {
                    correctCount++
                }
            }

            val totalQuestions = correctAnswers.size
            val resultText = "Ваш результат: $correctCount/$totalQuestions\n\n" +
                    when {
                        correctCount == totalQuestions -> "Отлично! 🎉"
                        correctCount > totalQuestions / 2 -> "Хорошо! 👍"
                        else -> "Попробуйте еще раз! 💪"
                    }

            binding.resultTextView.text = resultText
        }

        binding.restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}