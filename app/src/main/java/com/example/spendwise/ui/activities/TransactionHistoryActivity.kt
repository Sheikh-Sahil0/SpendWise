package com.example.spendwise.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivityTransactionHistoryBinding
import com.example.spendwise.databinding.ItemTransactionBinding
import com.example.spendwise.ui.adapters.TransactionAdapter
import com.google.android.material.snackbar.Snackbar

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
        setupRecyclerView()
    }

    private fun setupClickListeners() {
        // Back button
        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Filter button
        binding.filterButton.setOnClickListener {
            Snackbar.make(binding.root, "Filter coming soon!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.transactionsRecyclerView.adapter = TransactionAdapter { position, description ->
            showTransactionOptions(position, description)
        }

        // Hide loading and empty state
        binding.loadingIndicator.visibility = View.GONE
        binding.emptyStateLayout.visibility = View.GONE
    }

    private fun showTransactionOptions(position: Int, description: String) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(this)
            .setTitle(description)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editTransaction(position, description)
                    1 -> deleteTransaction(position, description)
                }
            }
            .show()
    }

    private fun editTransaction(position: Int, description: String) {
        Snackbar.make(binding.root, "Edit: $description", Snackbar.LENGTH_SHORT).show()
        // TODO: Navigate to EditTransactionActivity
    }

    private fun deleteTransaction(position: Int, description: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete \"$description\"?")
            .setPositiveButton("Delete") { _, _ ->
                Snackbar.make(binding.root, "Deleted: $description", Snackbar.LENGTH_SHORT).show()
                // TODO: Remove from database and refresh adapter
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}