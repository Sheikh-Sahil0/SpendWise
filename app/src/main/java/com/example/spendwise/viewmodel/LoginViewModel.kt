package com.example.spendwise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spendwise.utils.ValidationUtils

class LoginViewModel : ViewModel() {

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> get() = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    // Validate Email
    fun validateEmail(email: String) {
        _emailError.value = if (email.isEmpty()) {
            "Email is required"
        } else if (!ValidationUtils.isValidEmail(email)) {
            "Please enter a valid email"
        } else {
            null
        }
    }

    // Validate Password
    fun validatePassword(password: String) {
        _passwordError.value = when {
            password.isEmpty() -> "Password is required"
            !ValidationUtils.isValidPassword(password) ->
                "Min 8 chars, include number, uppercase & special char"
            else -> null
        }
    }

    // Fake login (replace with repo call later)
    fun login(email: String, password: String): Boolean {
        validateEmail(email)
        validatePassword(password)
        return _emailError.value == null && _passwordError.value == null
    }
}
