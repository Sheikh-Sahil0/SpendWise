package com.example.spendwise.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spendwise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views and setup
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        // Load dashboard data
        loadDashboardData()
    }

    private fun loadDashboardData() {
        // TODO: Load data from database/preferences
        // For now using placeholder data matching your XML
        binding.incomeAmount.text = "₹25,000"
        binding.expenseAmount.text = "₹18,500"
        binding.lastMonthSavings.text = "₹6,500"
        binding.totalSavings.text = "Total: ₹45,200"
        binding.transactionsCount.text = "47"
        binding.selectedMonth.text = "August 2025"
    }

    private fun setupClickListeners() {
        // Profile Picture Click - Navigate to ProfileActivity
        binding.profilePictureCard.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                // Pass current user data if needed
                putExtra("displayName", "Shaikh Sahil")
                putExtra("username", "shaikh_sahil")
                putExtra("email", "shaikhsahil@example.com")
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Month Selector Click
        binding.monthSelector.setOnClickListener {
            // TODO: Show month picker dialog
            showMonthPicker()
        }

        // Transaction History Button Click
        binding.transactionHistoryButton.setOnClickListener {
            // TODO: Navigate to TransactionHistoryActivity
            // val intent = Intent(this, TransactionHistoryActivity::class)
            // startActivity(intent)
            showPlaceholderMessage("Transaction History will be implemented later")
        }

        // Add Transaction Button Click
        binding.addTransactionButton.setOnClickListener {
            // TODO: Navigate to AddTransactionActivity
            // val intent = Intent(this, AddTransactionActivity::class)
            // startActivity(intent)
            showPlaceholderMessage("Add Transaction will be implemented later")
        }

        // Statistics Button Click
        binding.statisticsButton.setOnClickListener {
            // TODO: Navigate to StatisticsActivity
            // val intent = Intent(this, StatisticsActivity::class)
            // startActivity(intent)
            showPlaceholderMessage("Statistics will be implemented later")
        }
    }

    private fun showMonthPicker() {
        // TODO: Implement month picker
        showPlaceholderMessage("Month picker will be implemented later")
    }

    private fun showPlaceholderMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}