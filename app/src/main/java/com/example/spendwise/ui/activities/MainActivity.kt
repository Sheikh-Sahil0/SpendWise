package com.example.spendwise.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivityMainBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views and setup
        initViews()
        setupClickListeners()
        setupCharts()
    }

    private fun initViews() {
        // Load dashboard data
        binding.incomeAmount.text = "₹25,000"
        binding.expenseAmount.text = "₹18,500"
        binding.lastMonthSavings.text = "₹6,500"
        binding.totalSavings.text = "Total: ₹45,200"
        binding.transactionsCount.text = "47"
        binding.selectedMonth.text = "August 2025"
    }

    private fun setupCharts() {
        setupSpendingByCategory()
        setupBudgetChart()
    }

    private fun setupSpendingByCategory() {
        // Unique categories with their spent amounts
        val entries = listOf(
            PieEntry(4500f, "Food"),
            PieEntry(2500f, "Transport"),
            PieEntry(8000f, "Rent"),
            PieEntry(1200f, "Entertainment"),
            PieEntry(2000f, "Healthcare"),
            PieEntry(1500f, "Utilities"),
            PieEntry(1800f, "Education"),
            PieEntry(1000f, "Travel"),
            PieEntry(900f, "Others")
        )

        // Sum for center text
        val total = entries.sumOf { it.value.toDouble() }

        // Currency formatter (Indian locale) -> ₹4,500 etc.
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
            maximumFractionDigits = 0
        }

        // DataSet and colors
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList() + ColorTemplate.COLORFUL_COLORS.toList()
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE

        // Provide a ValueFormatter object (cannot pass lambda)
        val valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // value is the raw amount (e.g. 4500f) — format as currency
                return currencyFormatter.format(value.toDouble())
            }
        }

        val data = PieData(dataSet)
        data.setValueFormatter(valueFormatter)    // <-- correct usage (ValueFormatter instance)

        binding.pieChartView.apply {
            this.data = data
            description.isEnabled = false

            // We want actual ₹ values in slices (not percentages)
            setUsePercentValues(false)

            // Hide the category label inside slices (only show the value)
            setDrawEntryLabels(false)

            // Legend (category names) visible and wrap if many items
            legend.isEnabled = true
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.isWordWrapEnabled = true
            legend.textSize = 12f
            legend.textColor = ContextCompat.getColor(this@MainActivity, R.color.md_theme_light_onBackground)

            // Styling
            holeRadius = 40f
            transparentCircleRadius = 45f
            centerText = "Total Spent\n${currencyFormatter.format(total)}"
            setCenterTextSize(14f)

            animateY(800)
            invalidate()
        }
    }

    private fun setupBudgetChart() {
        val remaining = 6500f
        val spent = 18500f

        // Label ko blank rakhte hain taaki slice ke andar text (Remaining/Spent) na aaye
        val entries = listOf(
            PieEntry(remaining, ""),
            PieEntry(spent, "")
        )

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(Color.GREEN, Color.RED)

        // Slice ke andar sirf % hi dikhana hai
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(binding.budgetChartView))

        binding.budgetChartView.apply {
            this.data = data
            description.isEnabled = false
            setUsePercentValues(true)

            // Slice ke andar label text na dikhaye
            setDrawEntryLabels(false)

            // Half Pie look
            maxAngle = 180f
            rotationAngle = 180f
            holeRadius = 45f
            transparentCircleRadius = 50f

            // ✅ Legend me hi label + amount show karna hai
            legend.isEnabled = true
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.isWordWrapEnabled = true
            legend.textSize = 12f
            legend.textColor = ContextCompat.getColor(this@MainActivity, R.color.md_theme_light_onBackground)

            // Custom legend with amounts
            val remEntry = com.github.mikephil.charting.components.LegendEntry().apply {
                label = "Remaining: ₹${remaining.toInt()}"
                formColor = Color.GREEN
            }
            val spentEntry = com.github.mikephil.charting.components.LegendEntry().apply {
                label = "Spent: ₹${spent.toInt()}"
                formColor = Color.RED
            }
            legend.setCustom(arrayOf(remEntry, spentEntry))

            centerText = "Monthly Budget\n₹25,000"
            setCenterTextSize(14f)

            animateY(1000)
            invalidate()
        }
    }

    private fun setupClickListeners() {
        // Profile Picture Click - Navigate to ProfileActivity
        binding.profilePictureCard.setOnClickListener {
            try {
                val intent = Intent(this, ProfileActivity::class.java).apply {
                    putExtra("displayName", "Shaikh Sahil")
                    putExtra("username", "shaikh_sahil")
                    putExtra("email", "shaikhsahil@example.com")
                }
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            } catch (e: Exception) {
                e.printStackTrace()
                showPlaceholderMessage("Error opening profile: ${e.message}")
            }
        }

        // Month Selector Click
        binding.monthSelector.setOnClickListener {
            showMonthPicker()
        }

        binding.transactionHistoryButton.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity, TransactionHistoryActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                showPlaceholderMessage("Error opening transactions history: ${e.message}")
            }
        }

        // Fixed Add Transaction Button with error handling
        binding.addTransactionButton.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity, AddTransactionActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                showPlaceholderMessage("Error opening add transaction: ${e.message}")
            }
        }

        binding.statisticsButton.setOnClickListener {
            try {
                val intent = Intent(this@MainActivity, StatisticsActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                showPlaceholderMessage("Error opening add statistics: ${e.message}")
            }
        }
    }

    private fun showMonthPicker() {
        showPlaceholderMessage("Month picker will be implemented later")
    }

    private fun showPlaceholderMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }
}
