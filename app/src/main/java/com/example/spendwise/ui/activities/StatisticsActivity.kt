package com.example.spendwise.ui.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivityStatisticsBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.NumberFormat
import java.util.Locale

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 0
    }

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
        val months = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        // More realistic income data with curves (values in thousands)
        val incomeData = listOf(
            18f, 20.2f, 19.5f, 21f, 19.8f, 22.5f,
            21.2f, 23f, 24.5f, 26f, 25.2f, 23.8f
        )

        // Expense data that creates interesting curves
        val expenseData = listOf(
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
            circleRadius = 5f
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
            circleRadius = 5f
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
                valueFormatter = IndexAxisValueFormatter(months)
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

            // Refresh chart
            invalidate()
        }
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