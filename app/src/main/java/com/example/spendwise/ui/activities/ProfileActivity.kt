package com.example.spendwise.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivityProfileBinding
import com.google.android.material.snackbar.Snackbar

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    // Activity result launcher for edit profile
    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                val updatedDisplayName = data.getStringExtra("updated_display_name")
                val updatedUsername = data.getStringExtra("updated_username")

                // Update UI with new data
                updateProfileUI(updatedDisplayName, updatedUsername)
                showUpdateSuccessMessage()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views and setup
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        // Load user data
        loadUserData()
    }

    private fun loadUserData() {
        // Get data from intent or load from preferences/database
        val displayName = intent.getStringExtra("displayName") ?: "John Doe"
        val username = intent.getStringExtra("username") ?: "johndoe"
        val email = intent.getStringExtra("email") ?: "johndoe@example.com"

        // Set the data to UI
        binding.displayName.text = displayName
        binding.username.text = username
        binding.email.text = email
    }

    private fun setupClickListeners() {
        // Back Button Click - Navigate back to MainActivity
        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Edit Profile Card Click - Navigate to EditProfileActivity
        binding.editProfileCard.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                // Pass current user data to edit activity
                putExtra("displayName", binding.displayName.text.toString())
                putExtra("username", binding.username.text.toString())
                putExtra("email", binding.email.text.toString())
            }
            editProfileLauncher.launch(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Theme Toggle Buttons
        setupThemeButtons()

        // Logout Button Click
        binding.logoutButton.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun setupThemeButtons() {
        // Light Theme Button
        binding.lightThemeButton.setOnClickListener {
            // TODO: Implement theme change to light
            showPlaceholderMessage("Light theme selected")
        }

        // Dark Theme Button
        binding.darkThemeButton.setOnClickListener {
            // TODO: Implement theme change to dark
            showPlaceholderMessage("Dark theme selected")
        }

        // System Theme Button
        binding.systemThemeButton.setOnClickListener {
            // TODO: Implement system theme follow
            showPlaceholderMessage("System theme selected")
        }

        // Set default selection (you can get current theme from preferences)
        binding.systemThemeButton.isChecked = true
    }

    private fun updateProfileUI(displayName: String?, username: String?) {
        displayName?.let {
            binding.displayName.text = it
        }
        username?.let {
            binding.username.text = it
        }
    }

    private fun showUpdateSuccessMessage() {
        Snackbar.make(
            binding.root,
            "Profile updated successfully",
            Snackbar.LENGTH_SHORT
        ).setAnchorView(binding.logoutButton)
            .show()
    }

    private fun showLogoutConfirmation() {
        // TODO: Show confirmation dialog and handle logout
        showPlaceholderMessage("Logout functionality will be implemented later")
    }

    private fun showPlaceholderMessage(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}