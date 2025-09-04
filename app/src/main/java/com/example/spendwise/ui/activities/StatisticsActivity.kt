package com.example.spendwise.ui.activities

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivityStatisticsBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.NumberFormat
import java.util.Locale

class StatisticsActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var binding: ActivityStatisticsBinding
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 0
    }

    // Store data for click handling
    private lateinit var months: Array<String>
    private lateinit var incomeData: List<Float>
    private lateinit var expenseData: List<Float>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        initViews()
        setupClickListeners()
        setupLineChart()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        // Set sample data
        binding.selectedYear.text = "2025"
        binding.totalIncomeAmount.text = "₹2,85,000"
        binding.totalExpenseAmount.text = "₹2,07,500"
        binding.netSavingsAmount.text = "₹77,500"
        binding.netSavingsLabel.text = "Net Savings (2025)"
    }

    private fun setupClickListeners() {
        binding.yearSelector.setOnClickListener {
            showYearPicker()
        }
    }

    private fun setupLineChart() {
        // Sample data for 12 months (similar to your screenshot pattern)
        months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        // More realistic income data with curves (values in thousands)
        incomeData = listOf(
            18f, 20.2f, 19.5f, 21f, 19.8f, 22.5f,
            21.2f, 23f, 24.5f, 26f, 25.2f, 23.8f
        )

        // Expense data that creates interesting curves
        expenseData = listOf(
            15f, 12.8f, 14.2f, 16f, 15.5f, 17.2f,
            16.8f, 18.5f, 17f, 19.2f, 18.8f, 16.5f
        )

        // Create entries for income line
        val incomeEntries = incomeData.mapIndexed { index, value ->
            Entry(index.toFloat(), value)
        }

        // Create entries for expense line
        val expenseEntries = expenseData.mapIndexed { index, value ->
            Entry(index.toFloat(), value)
        }

        // Create income dataset with smooth curves
        val incomeDataSet = LineDataSet(incomeEntries, "Income").apply {
            // Enable cubic bezier curves for smooth lines
            mode = LineDataSet.Mode.CUBIC_BEZIER
            cubicIntensity = 0.2f

            color = ContextCompat.getColor(this@StatisticsActivity, R.color.income_green)
            setCircleColor(ContextCompat.getColor(this@StatisticsActivity, R.color.income_green))
            lineWidth = 3f
            circleRadius = 6f  // Made slightly larger for better touch target
            setDrawCircleHole(false)
            setDrawValues(false) // Hide values on points for cleaner look

            // Add fill under the line for better visual appeal
            setDrawFilled(true)
            fillAlpha = 30
            fillColor = ContextCompat.getColor(this@StatisticsActivity, R.color.income_green)
        }

        // Create expense dataset with smooth curves
        val expenseDataSet = LineDataSet(expenseEntries, "Expense").apply {
            // Enable cubic bezier curves for smooth lines
            mode = LineDataSet.Mode.CUBIC_BEZIER
            cubicIntensity = 0.2f

            color = ContextCompat.getColor(this@StatisticsActivity, R.color.expense_red)
            setCircleColor(ContextCompat.getColor(this@StatisticsActivity, R.color.expense_red))
            lineWidth = 3f
            circleRadius = 6f  // Made slightly larger for better touch target
            setDrawCircleHole(false)
            setDrawValues(false) // Hide values on points for cleaner look

            // Add fill under the line
            setDrawFilled(true)
            fillAlpha = 30
            fillColor = ContextCompat.getColor(this@StatisticsActivity, R.color.expense_red)
        }

        // Create line data
        val lineData = LineData(incomeDataSet, expenseDataSet)

        // Configure the chart with enhanced settings
        binding.lineChartView.apply {
            data = lineData

            // Chart appearance
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true) // Enable scaling for better interaction
            setPinchZoom(true) // Enable pinch to zoom
            setDrawGridBackground(false)

            // Enable zooming and scrolling
            isDoubleTapToZoomEnabled = true

            // Set viewport to show all data initially
            setVisibleXRangeMaximum(12f)
            setVisibleXRangeMinimum(6f)

            // Move to show latest data
            moveViewToX(11f)

            // X-axis configuration
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(true)
                gridColor = Color.parseColor("#E0E0E0")
                gridLineWidth = 0.5f
                granularity = 1f
                textColor = ContextCompat.getColor(this@StatisticsActivity, R.color.md_theme_light_onSurfaceVariant)
                textSize = 11f
                valueFormatter = IndexAxisValueFormatter(arrayOf(
                    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                ))
                setLabelCount(12, false)
                setAvoidFirstLastClipping(true)
            }

            // Left Y-axis configuration
            axisLeft.apply {
                setDrawGridLines(true)
                gridColor = Color.parseColor("#E0E0E0")
                gridLineWidth = 0.5f
                textColor = ContextCompat.getColor(this@StatisticsActivity, R.color.md_theme_light_onSurfaceVariant)
                textSize = 11f

                // Dynamic axis range based on data
                val maxIncome = incomeData.maxOrNull() ?: 30f
                val maxExpense = expenseData.maxOrNull() ?: 20f
                val maxValue = maxOf(maxIncome, maxExpense)

                axisMinimum = 0f
                axisMaximum = maxValue * 1.1f

                // Format Y-axis labels as currency
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "₹${(value * 1000).toInt()}"
                    }
                }
            }

            // Right Y-axis (disable)
            axisRight.isEnabled = false

            // Legend configuration
            legend.apply {
                isEnabled = true
                orientation = Legend.LegendOrientation.HORIZONTAL
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                textSize = 12f
                textColor = ContextCompat.getColor(this@StatisticsActivity, R.color.md_theme_light_onSurface)
                form = Legend.LegendForm.LINE
                formSize = 12f
                xEntrySpace = 20f
            }

            // Enhanced animations
            animateX(1500)
            animateY(1500)

            // Set click listener
            setOnChartValueSelectedListener(this@StatisticsActivity)

            // Refresh chart
            invalidate()
        }
    }

    // Handle chart value selection (dot clicks)
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let { entry ->
            val monthIndex = entry.x.toInt()
            if (monthIndex >= 0 && monthIndex < months.size) {
                showMonthDetailDialog(
                    monthName = months[monthIndex],
                    income = incomeData[monthIndex] * 1000, // Convert back to actual value
                    expense = expenseData[monthIndex] * 1000
                )
            }
        }
    }

    override fun onNothingSelected() {
        // Handle when nothing is selected (optional)
    }

    private fun showMonthDetailDialog(monthName: String, income: Float, expense: Float) {
        val savings = income - expense
        val savingsColor = if (savings >= 0) R.color.savings_blue else R.color.expense_red
        val savingsIcon = if (savings >= 0) R.drawable.ic_savings else R.drawable.ic_trending_down

        // Try to use custom layout first, fallback to simple dialog if layout doesn't exist
        try {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_month_detail, null)

            // Populate the custom dialog
            dialogView.findViewById<TextView>(R.id.monthTitle)?.text = "$monthName 2025"
            dialogView.findViewById<TextView>(R.id.incomeAmount)?.text = "₹${String.format("%,.0f", income)}"
            dialogView.findViewById<TextView>(R.id.expenseAmount)?.text = "₹${String.format("%,.0f", expense)}"
            dialogView.findViewById<TextView>(R.id.savingsAmount)?.apply {
                text = "₹${String.format("%,.0f", savings)}"
                setTextColor(ContextCompat.getColor(this@StatisticsActivity, savingsColor))
            }

            dialogView.findViewById<TextView>(R.id.savingsLabel)?.text = if (savings >= 0) "Net Savings" else "Deficit"
            dialogView.findViewById<ImageView>(R.id.savingsIcon)?.apply {
                setImageResource(savingsIcon)
                setColorFilter(ContextCompat.getColor(this@StatisticsActivity, savingsColor))
            }

            val dialog = MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setPositiveButton("Got it!") { dialog, _ -> dialog.dismiss() }
                .create()

            dialog.show()

            // Style the button
            dialog.getButton(Dialog.BUTTON_POSITIVE)?.setTextColor(
                ContextCompat.getColor(this, R.color.savings_blue)
            )

        } catch (e: Exception) {
            // Fallback to simple dialog if custom layout is not available
            showSimpleMonthDetailDialog(monthName, income, expense, savings)
        }
    }

    private fun showSimpleMonthDetailDialog(monthName: String, income: Float, expense: Float, savings: Float) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("$monthName Details")
            .setMessage(buildMonthDetailMessage(monthName, income, expense, savings))
            .setPositiveButton("Got it!") { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.show()

        dialog.getButton(Dialog.BUTTON_POSITIVE)?.setTextColor(
            ContextCompat.getColor(this, R.color.savings_blue)
        )
    }

    private fun buildMonthDetailMessage(monthName: String, income: Float, expense: Float, savings: Float): String {
        val incomeFormatted = "₹${String.format("%,.0f", income)}"
        val expenseFormatted = "₹${String.format("%,.0f", expense)}"
        val savingsFormatted = "₹${String.format("%,.0f", savings)}"
        val savingsStatus = if (savings >= 0) "Saved" else "Deficit"

        return """
            Month: $monthName
            
            Income: $incomeFormatted
            Expense: $expenseFormatted
            
            $savingsStatus: $savingsFormatted
            
            ${if (savings >= 0) "Great job on saving this month!" else "Consider reviewing your expenses next month."}
        """.trimIndent()
    }

    private fun showYearPicker() {
        showPlaceholderMessage("Year picker will be implemented later")
    }

    private fun showPlaceholderMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }
}