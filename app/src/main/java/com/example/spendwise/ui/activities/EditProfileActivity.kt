package com.example.spendwise.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spendwise.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        // Load current user data (you can get this from SharedPreferences, database, or intent extras)
        loadUserData()
    }

    private fun loadUserData() {
        // For now, using placeholder data
        // In real implementation, get from your data source
        binding.displayNameEditText.setText("John Doe")
        binding.usernameEditText.setText("johndoe")
        binding.emailEditText.setText("johndoe@example.com")
    }

    private fun setupClickListeners() {
        // Back button click
        binding.backButton.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Save button in toolbar
        binding.saveButton.setOnClickListener {
            // TODO: Implement save functionality
            showSaveMessage()
        }

        // Profile picture click - change photo
        binding.profilePictureCard.setOnClickListener {
            // TODO: Implement image picker
            // For now, just show a message
            showImagePickerPlaceholder()
        }

        // Camera icon click - change photo
        binding.cameraIconCard.setOnClickListener {
            // TODO: Implement image picker
            showImagePickerPlaceholder()
        }

        // Save changes button
        binding.saveChangesButton.setOnClickListener {
            // TODO: Implement save functionality
            showSaveMessage()
        }

        // Cancel button
        binding.cancelButton.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    private fun showSaveMessage() {
        // TODO: Implement actual save functionality
        // For now, just finish activity with success result
        val resultIntent = Intent().apply {
            putExtra("updated_display_name", binding.displayNameEditText.text.toString())
            putExtra("updated_username", binding.usernameEditText.text.toString())
        }
        setResult(RESULT_OK, resultIntent)
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun showImagePickerPlaceholder() {
        // TODO: Implement image picker functionality
        // For now, just show a toast or snackbar
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            "Image picker functionality will be implemented later",
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }

    // Validate input fields
    private fun validateInputs(): Boolean {
        var isValid = true

        val displayName = binding.displayNameEditText.text.toString().trim()
        val username = binding.usernameEditText.text.toString().trim()

        // Display name validation
        if (displayName.isEmpty()) {
            binding.displayNameInputLayout.error = "Display name is required"
            isValid = false
        } else {
            binding.displayNameInputLayout.error = null
        }

        // Username validation
        if (username.isEmpty()) {
            binding.usernameInputLayout.error = "Username is required"
            isValid = false
        } else if (username.length < 3) {
            binding.usernameInputLayout.error = "Username must be at least 3 characters"
            isValid = false
        } else if (!username.matches(Regex("^[a-zA-Z0-9_]+$"))) {
            binding.usernameInputLayout.error = "Username can only contain letters, numbers, and underscores"
            isValid = false
        } else {
            binding.usernameInputLayout.error = null
        }

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        // No need to set binding to null in newer view binding versions
    }
}