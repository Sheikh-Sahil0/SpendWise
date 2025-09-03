package com.example.spendwise.ui.adapters

import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwise.R
import com.example.spendwise.databinding.ItemTransactionBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter(
    private val onDeleteClick: (Int, String) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    // Mock data - just arrays of strings and numbers
    private val descriptions = arrayOf(
        "Grocery Shopping", "Uber Ride", "Monthly Salary", "Netflix", "Coffee",
        "Restaurant", "Gas Station", "Freelance Work", "Electricity Bill", "Movie Tickets",
        "Amazon Purchase", "Bonus", "Medical Bills", "Pizza Delivery", "Gym Membership"
    )

    private val categories = arrayOf(
        "Food & Dining", "Transportation", "Income", "Entertainment", "Food & Dining",
        "Food & Dining", "Transportation", "Income", "Utilities", "Entertainment",
        "Shopping", "Income", "Healthcare", "Food & Dining", "Health"
    )

    private val amounts = arrayOf(
        "₹2,500", "₹450", "+₹25,000", "₹199", "₹280",
        "₹1,500", "₹2,000", "+₹8,000", "₹3,200", "₹600",
        "₹1,200", "+₹5,000", "₹1,800", "₹750", "₹2,500"
    )

    private val dates = arrayOf(
        "Sep 03, 2025", "Sep 02, 2025", "Sep 01, 2025", "Aug 31, 2025", "Aug 30, 2025",
        "Aug 29, 2025", "Aug 28, 2025", "Aug 27, 2025", "Aug 26, 2025", "Aug 25, 2025",
        "Aug 24, 2025", "Aug 23, 2025", "Aug 22, 2025", "Aug 21, 2025", "Aug 20, 2025"
    )

    // Income positions (for green color and up arrow)
    private val incomePositions = setOf(2, 7, 11)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = descriptions.size

    inner class ViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.transactionDescription.text = descriptions[position]
            binding.transactionCategory.text = categories[position]
            binding.transactionDate.text = dates[position]
            binding.transactionAmount.text = amounts[position]

            // Set icon and color based on whether it's income or expense
            if (incomePositions.contains(position)) {
                // Income transaction
                binding.typeIcon.setImageResource(R.drawable.ic_trending_up)
                binding.transactionAmount.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.income_green)
                )
                binding.transactionCategory.visibility = View.GONE // Hide category for income
            } else {
                // Expense transaction
                binding.typeIcon.setImageResource(R.drawable.ic_trending_down)
                binding.transactionAmount.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.expense_red)
                )
                binding.transactionCategory.visibility = View.VISIBLE // Show category for expenses
            }

            // Click listeners
            binding.deleteButton.setOnClickListener {
                onDeleteClick(position, descriptions[position])
            }

            binding.root.setOnClickListener {
                Snackbar.make(binding.root, "Clicked: ${descriptions[position]}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}