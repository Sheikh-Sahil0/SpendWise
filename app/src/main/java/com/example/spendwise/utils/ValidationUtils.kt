package com.example.spendwise.utils

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidUsername(username: String): Boolean {
        // only lowercase letters + underscores
        return Regex("^[a-z_]{3,}$").matches(username)
    }

    fun isValidPassword(password: String): Boolean {
        // min 8 chars, at least 1 digit, 1 uppercase, 1 lowercase, 1 special char
        return Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#\$%^&+=!]).{8,}$")
            .matches(password)
    }

    fun isValidDisplayName(name: String): Boolean {
        // 2–30 characters, only letters, numbers and spaces allowed
        return Regex("^[A-Za-z0-9 ]{2,30}$").matches(name)
    }
}
