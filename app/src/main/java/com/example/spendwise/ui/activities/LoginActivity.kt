package com.example.spendwise.ui.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.spendwise.ui.activities.MainActivity
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivityLoginBinding
import com.example.spendwise.viewmodel.LoginViewModel
import com.example.spendwise.viewmodel.SignupViewModel
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var isFirstLaunch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize view binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Start entrance animations
        startEntranceAnimations()

        // Start particle animations
        startParticleAnimations()

        // Start background gradient animations
        startBackgroundAnimations()

        // setup
        setupValidationObservers()
        setupTextWatchers()
        setupClickListeners()
    }

    private fun startEntranceAnimations() {
        // Initial state - everything hidden
        binding.logoText.alpha = 0f
        binding.logoText.translationY = -100f
        binding.subtitleText.alpha = 0f
        binding.subtitleText.translationY = -50f
        binding.loginCard.alpha = 0f
        binding.loginCard.translationY = 100f
        binding.loginCard.scaleX = 0.9f
        binding.loginCard.scaleY = 0.9f

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

        // Subtitle entrance animation
        Handler(Looper.getMainLooper()).postDelayed({
            val subtitleFadeIn = ObjectAnimator.ofFloat(binding.subtitleText, "alpha", 0f, 1f)
            val subtitleSlideDown = ObjectAnimator.ofFloat(binding.subtitleText, "translationY", -50f, 0f)

            AnimatorSet().apply {
                playTogether(subtitleFadeIn, subtitleSlideDown)
                duration = 600
                interpolator = DecelerateInterpolator()
                start()
            }
        }, 300)

        // Card entrance animation
        Handler(Looper.getMainLooper()).postDelayed({
            val cardFadeIn = ObjectAnimator.ofFloat(binding.loginCard, "alpha", 0f, 1f)
            val cardSlideUp = ObjectAnimator.ofFloat(binding.loginCard, "translationY", 100f, 0f)
            val cardScaleX = ObjectAnimator.ofFloat(binding.loginCard, "scaleX", 0.9f, 1f)
            val cardScaleY = ObjectAnimator.ofFloat(binding.loginCard, "scaleY", 0.9f, 1f)

            AnimatorSet().apply {
                playTogether(cardFadeIn, cardSlideUp, cardScaleX, cardScaleY)
                duration = 1000
                interpolator = DecelerateInterpolator()
                start()
            }
        }, 500)
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
        // Use existing animation files with variations
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

    private fun startBackgroundAnimations() {
        // Animate the gradient circles for more dynamic background

        // First gradient circle - slow rotation and scale pulsing
        val gradientRotation1 = ObjectAnimator.ofFloat(binding.backgroundGradient1, "rotation", 0f, 360f).apply {
            duration = 15000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

        val gradientScale1 = ObjectAnimator.ofFloat(
            binding.backgroundGradient1, "scaleX", 1f, 1.1f, 0.95f, 1f
        ).apply {
            duration = 8000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
        }

        val gradientScaleY1 = ObjectAnimator.ofFloat(
            binding.backgroundGradient1, "scaleY", 1f, 1.1f, 0.95f, 1f
        ).apply {
            duration = 8000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Second gradient circle - counter rotation and different scale timing
        val gradientRotation2 = ObjectAnimator.ofFloat(binding.backgroundGradient2, "rotation", 360f, 0f).apply {
            duration = 20000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

        val gradientScale2 = ObjectAnimator.ofFloat(
            binding.backgroundGradient2, "scaleX", 1f, 0.9f, 1.15f, 1f
        ).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 2000
        }

        val gradientScaleY2 = ObjectAnimator.ofFloat(
            binding.backgroundGradient2, "scaleY", 1f, 0.9f, 1.15f, 1f
        ).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = 2000
        }

        // Start all gradient animations
        gradientRotation1.start()
        gradientScale1.start()
        gradientScaleY1.start()

        gradientRotation2.start()
        gradientScale2.start()
        gradientScaleY2.start()
    }

    // Validation
    private fun setupValidationObservers() {
        viewModel.emailError.observe(this) { binding.emailInputLayout.error = it }
        viewModel.passwordError.observe(this) { binding.passwordInputLayout.error = it }
    }

    private fun setupTextWatchers() {
        binding.emailEditText.addTextChangedListener { viewModel.validateEmail(it.toString()) }
        binding.passwordEditText.addTextChangedListener { viewModel.validatePassword(it.toString()) }
    }

    private fun setupClickListeners() {
        // Login button click with animation
        binding.loginButton.setOnClickListener {
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

            // Gather inputs
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            // Trigger final validation in VM
            viewModel.validateEmail(email)
            viewModel.validatePassword(password)

            // Proceed only if all errors are null
            if (binding.emailInputLayout.error == null &&
                binding.passwordInputLayout.error == null
            ) {
                // Handle login logic here
                performLogin()
            }
        }

        // Signup link click with animation
        binding.signupLinkText.setOnClickListener {
            val scaleX = ObjectAnimator.ofFloat(it, "scaleX", 1f, 1.1f, 1f)
            val scaleY = ObjectAnimator.ofFloat(it, "scaleY", 1f, 1.1f, 1f)

            AnimatorSet().apply {
                playTogether(scaleX, scaleY)
                duration = 200
                start()
            }

            // Navigate to signup activity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun performLogin() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString()

        // Add your validation and login logic here
        if (validateInputs(email, password)) {
            // Proceed with login
            // Example: Navigate to MainActivity after successful login
             val intent = Intent(this, MainActivity::class.java)
             startActivity(intent)
             finish()
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        // Reset error states
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null

        var isValid = true

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

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up binding reference
        // binding = null (not needed for activities, only fragments)
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstLaunch) {
            startEntranceAnimations()
            startParticleAnimations()
            startBackgroundAnimations()
        }
        isFirstLaunch = false
    }
}