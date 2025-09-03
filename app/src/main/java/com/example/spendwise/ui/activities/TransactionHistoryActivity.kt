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
import com.example.spendwise.databinding.DialogDeleteTransactionBinding
import com.example.spendwise.ui.adapters.TransactionAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        showCustomDeleteDialog(position, description)
    }

    private fun showCustomDeleteDialog(position: Int, description: String) {
        val dialogBinding = DialogDeleteTransactionBinding.inflate(layoutInflater)

        // Set transaction details in the dialog
        dialogBinding.transactionTitle.text = description
        dialogBinding.transactionDescription.text = "This action cannot be undone. The transaction will be permanently removed from your records."

        val dialog = MaterialAlertDialogBuilder(this, R.style.CustomAlertDialogTheme)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        // Set click listeners for custom buttons
        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.deleteButton.setOnClickListener {
            dialog.dismiss()
            deleteTransaction(position, description)
        }

        // Show the dialog
        dialog.show()

        // Optional: Set dialog window properties for better appearance
        dialog.window?.let { window ->
            window.setBackgroundDrawableResource(R.drawable.dialog_background)
            // You can also set custom width/height if needed
            // val params = window.attributes
            // params.width = ViewGroup.LayoutParams.MATCH_PARENT
            // window.attributes = params
        }
    }

    private fun deleteTransaction(position: Int, description: String) {
        Snackbar.make(binding.root, "Deleted: $description", Snackbar.LENGTH_SHORT).show()
        // TODO: Remove from database and refresh adapter
    }
}