package com.example.spendwise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spendwise.utils.ValidationUtils

class SignupViewModel : ViewModel() {

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _displayNameError = MutableLiveData<String?>()
    val displayNameError: LiveData<String?> = _displayNameError

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> = _usernameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<String?>()
    val confirmPasswordError: LiveData<String?> = _confirmPasswordError

    fun validateEmail(email: String) {
        _emailError.value = when {
            email.isEmpty() -> "Email is required"
            !ValidationUtils.isValidEmail(email) -> "Invalid email format"
            else -> null
        }
    }

    fun validateUsername(username: String) {
        _usernameError.value = when {
            username.isEmpty() -> "Username is required"
            !ValidationUtils.isValidUsername(username) -> "Only lowercase & underscores allowed"
            else -> null
        }
    }

    fun validateDisplayName(name: String) {
        _displayNameError.value = when {
            name.isEmpty() -> "Display name is required"
            !ValidationUtils.isValidDisplayName(name) ->
                "2–30 characters (letters, numbers, spaces only)"
            else -> null
        }
    }

    fun validatePassword(password: String) {
        _passwordError.value = when {
            password.isEmpty() -> "Password is required"
            !ValidationUtils.isValidPassword(password) ->
                "Min 8 chars, include number, uppercase & special char"
            else -> null
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String) {
        _confirmPasswordError.value = when {
            confirmPassword.isEmpty() -> "Confirm your password"
            confirmPassword != password -> "Passwords do not match"
            else -> null
        }
    }
}
