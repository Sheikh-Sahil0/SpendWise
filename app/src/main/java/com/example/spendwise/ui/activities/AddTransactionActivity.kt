package com.example.spendwise.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivityAddTransactionBinding
import com.example.spendwise.ui.adapters.TransactionPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var pagerAdapter: TransactionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewPager()
        setupClickListeners()
    }

    private fun setupViewPager() {
        pagerAdapter = TransactionPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Expense"
                    tab.setIcon(R.drawable.ic_trending_down)
                }
                1 -> {
                    tab.text = "Income"
                    tab.setIcon(R.drawable.ic_trending_up)
                }
            }
        }.attach()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        binding.saveButton.setOnClickListener {
            Snackbar.make(binding.root, "Transaction saved successfully!", Snackbar.LENGTH_SHORT).show()

            // Use Handler with Looper.getMainLooper() instead of deprecated Handler()
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }, 1000)
        }
    }
}