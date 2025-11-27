package com.example.kotlin3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin3.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val correctCount = intent.getIntExtra("correct_answers", 0)
        val totalQuestions = intent.getIntExtra("total_questions", 0)
        userName = intent.getStringExtra("user_name") ?: ""

        val percentage = (correctCount.toDouble() / totalQuestions) * 100

        setupResultLottieAnimation()

        val resultText = """
            Пользователь: $userName
            Ваш результат: $correctCount/$totalQuestions
            Процент правильных ответов: ${"%.1f".format(percentage)}%
            
            ${when {
            percentage == 100.0 -> "Отлично! 🎉"
            percentage >= 70.0 -> "Хорошо! 👍"
            percentage >= 50.0 -> "Удовлетворительно 👌"
            else -> "Попробуйте еще раз! 💪"
        }}
        """.trimIndent()

        binding.resultTextView.text = resultText

        binding.restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("user_name", userName)
            startActivity(intent)
        }
    }

    private fun setupResultLottieAnimation() {
        binding.resultAnimationView.setAnimation(R.raw.result_animation)
        binding.resultAnimationView.playAnimation()
        binding.resultAnimationView.repeatCount = 0
        binding.resultAnimationView.speed = 1.0f
    }
}