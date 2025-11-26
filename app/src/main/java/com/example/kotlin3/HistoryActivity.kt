package com.example.kotlin3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin3.data.AppDatabase
import com.example.kotlin3.databinding.ActivityHistoryBinding
import com.example.kotlin3.repository.TestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var repository: TestRepository
    private lateinit var adapter: TestResultAdapter
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("user_name") ?: ""

        val database = AppDatabase.getInstance(this)
        repository = TestRepository(database)

        setupRecyclerView()
        loadResults()
        loadAveragePercentage()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = TestResultAdapter()
        binding.resultsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.resultsRecyclerView.adapter = adapter
    }

    private fun loadResults() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.getResultsByUser(userName).collect { results ->
                adapter.submitList(results.reversed())
                binding.emptyTextView.visibility = if (results.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            }
        }
    }

    private fun loadAveragePercentage() {
        CoroutineScope(Dispatchers.IO).launch {
            val average = repository.getAveragePercentage(userName)
            runOnUiThread {
                binding.averageTextView.text = if (average != null) {
                    "Средний результат: ${"%.1f".format(average)}%"
                } else {
                    "Нет данных для статистики"
                }
            }
        }
    }
}