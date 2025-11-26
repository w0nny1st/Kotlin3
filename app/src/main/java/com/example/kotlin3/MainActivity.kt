package com.example.kotlin3

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlin3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("my_prefs", MODE_PRIVATE)

        val savedName = sharedPref.getString("user_name", "")
        binding.nameEditText.setText(savedName)

        binding.startButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isNotEmpty()) {
                sharedPref.edit().putString("user_name", name).apply()

                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show()
            }
        }
        binding.historyButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this, HistoryActivity::class.java)
                intent.putExtra("user_name", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите имя для просмотра истории", Toast.LENGTH_SHORT).show()
            }
        }
    }
}