package com.example.spendwise.ui.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize view binding
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Start entrance animations
        startEntranceAnimations()

        // Start particle animations
        startParticleAnimations()

        setupClickListeners()
    }

    private fun startEntranceAnimations() {
        // Initial state - everything hidden
        binding.logoText.alpha = 0f
        binding.logoText.translationY = -100f
        binding.registerCard.alpha = 0f
        binding.registerCard.translationY = 100f
        binding.registerCard.scaleX = 0.9f
        binding.registerCard.scaleY = 0.9f

        // Logo entrance animation
        Handler(Looper.getMainLooper()).postDelayed({
            val logoFadeIn = ObjectAnimator.ofFloat(binding.logoText, "alpha", 0f, 1f)
            val logoSlideDown = ObjectAnimator.ofFloat(binding.logoText, "translationY", -100f, 0f)

            AnimatorSet().apply {
                playTogether(logoFadeIn, logoSlideDown)
                duration = 800
                interpolator = OvershootInterpolator(1.2f)
                start()
            }
        }, 100)

        // Card entrance animation
        Handler(Looper.getMainLooper()).postDelayed({
            val cardFadeIn = ObjectAnimator.ofFloat(binding.registerCard, "alpha", 0f, 1f)
            val cardSlideUp = ObjectAnimator.ofFloat(binding.registerCard, "translationY", 100f, 0f)
            val cardScaleX = ObjectAnimator.ofFloat(binding.registerCard, "scaleX", 0.9f, 1f)
            val cardScaleY = ObjectAnimator.ofFloat(binding.registerCard, "scaleY", 0.9f, 1f)

            AnimatorSet().apply {
                playTogether(cardFadeIn, cardSlideUp, cardScaleX, cardScaleY)
                duration = 1000
                interpolator = DecelerateInterpolator()
                start()
            }
        }, 400)
    }

    private fun startParticleAnimations() {
        val particles = listOf(
            binding.particle1, binding.particle2, binding.particle3, binding.particle4,
            binding.particle5, binding.particle6, binding.particle7, binding.particle8,
            binding.particle9, binding.particle10, binding.particle11, binding.particle12,
            binding.particle13, binding.particle14, binding.particle15, binding.particle16,
            binding.particle17, binding.particle18, binding.particle19, binding.particle20
        )

        particles.forEachIndexed { index, particle ->
            // Staggered start delay
            Handler(Looper.getMainLooper()).postDelayed({
                startParticleAnimationWithExistingFiles(particle, index)
            }, (index * 100L))
        }
    }

    private fun startParticleAnimationWithExistingFiles(particle: ImageView, index: Int) {
        // Use your existing animation files with variations
        when (index % 3) {
            0 -> {
                // Use particle_float.xml animation
                val floatAnimation = AnimationUtils.loadAnimation(this, R.anim.particle_float)
                particle.startAnimation(floatAnimation)
            }
            1 -> {
                // Use float_up_down.xml animation
                val upDownAnimation = AnimationUtils.loadAnimation(this, R.anim.float_up_down)
                particle.startAnimation(upDownAnimation)
            }
            2 -> {
                // Use pulse_animation.xml for some particles
                val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation)
                particle.startAnimation(pulseAnimation)
            }
        }

        // Add additional rotation for more dynamic effect
        val rotation = ObjectAnimator.ofFloat(particle, "rotation", 0f, 360f).apply {
            duration = (8000 + index * 500).toLong()
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            startDelay = (index * 200L)
        }
        rotation.start()

        // Add subtle horizontal drift
        val driftX = ObjectAnimator.ofFloat(
            particle, "translationX",
            0f, (10f - (index * 2f)), (-5f + (index * 1f)), 0f
        ).apply {
            duration = (4000 + index * 300).toLong()
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = (index * 150L)
        }
        driftX.start()
    }

    private fun setupClickListeners() {
        // Sign up button click with animation
        binding.signUpButton.setOnClickListener {
            // Button press animation
            val scaleDown = ObjectAnimator.ofFloat(it, "scaleX", 1f, 0.95f)
            val scaleDownY = ObjectAnimator.ofFloat(it, "scaleY", 1f, 0.95f)
            val scaleUp = ObjectAnimator.ofFloat(it, "scaleX", 0.95f, 1f)
            val scaleUpY = ObjectAnimator.ofFloat(it, "scaleY", 0.95f, 1f)

            AnimatorSet().apply {
                play(scaleDown).with(scaleDownY)
                play(scaleUp).with(scaleUpY).after(scaleDown)
                duration = 100
                start()
            }

            // Handle sign up logic here
            performSignUp()
        }

        // Login link click with subtle animation
        binding.loginLinkText.setOnClickListener {
            val scaleX = ObjectAnimator.ofFloat(it, "scaleX", 1f, 1.1f, 1f)
            val scaleY = ObjectAnimator.ofFloat(it, "scaleY", 1f, 1.1f, 1f)

            AnimatorSet().apply {
                playTogether(scaleX, scaleY)
                duration = 200
                start()
            }

            // Navigate to login activity
            finish() // or start LoginActivity
        }
    }

    private fun performSignUp() {
        val username = binding.usernameEditText.text.toString().trim()
        val displayName = binding.displayNameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        // Add your validation and signup logic here
        if (validateInputs(username, displayName, email, password, confirmPassword)) {
            // Proceed with signup
        }
    }

    private fun validateInputs(username: String, displayName: String, email: String, password: String, confirmPassword: String): Boolean {
        // Reset error states
        binding.usernameInputLayout.error = null
        binding.displayNameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordInputLayout.error = null

        var isValid = true

        if (username.isEmpty()) {
            binding.usernameInputLayout.error = "Username is required"
            isValid = false
        }

        if (displayName.isEmpty()) {
            binding.displayNameInputLayout.error = "Display name is required"
            isValid = false
        }

        if (email.isEmpty()) {
            binding.emailInputLayout.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = "Please enter a valid email"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        }

        if (confirmPassword != password) {
            binding.confirmPasswordInputLayout.error = "Passwords do not match"
            isValid = false
        }

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up binding reference
        // binding = null (not needed for activities, only fragments)
    }
}