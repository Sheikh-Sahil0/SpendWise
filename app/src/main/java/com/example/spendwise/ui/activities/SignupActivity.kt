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
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivitySignupBinding
import com.example.spendwise.viewmodel.SignupViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Animations
        startEntranceAnimations()
        startParticleAnimations()
        startBackgroundAnimations()

        // setup
        setupValidationObservers()
        setupTextWatchers()
        setupClickListeners()
    }

    // ------------------- Animations -------------------
    private fun startEntranceAnimations() {
        binding.logoText.alpha = 0f
        binding.logoText.translationY = -100f
        binding.subtitleText.alpha = 0f
        binding.subtitleText.translationY = -50f
        binding.registerCard.alpha = 0f
        binding.registerCard.translationY = 100f
        binding.registerCard.scaleX = 0.9f
        binding.registerCard.scaleY = 0.9f

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

        Handler(Looper.getMainLooper()).postDelayed({
            val subtitleFadeIn = ObjectAnimator.ofFloat(binding.subtitleText, "alpha", 0f, 1f)
            val subtitleSlideDown =
                ObjectAnimator.ofFloat(binding.subtitleText, "translationY", -50f, 0f)
            AnimatorSet().apply {
                playTogether(subtitleFadeIn, subtitleSlideDown)
                duration = 600
                interpolator = DecelerateInterpolator()
                start()
            }
        }, 300)

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
            Handler(Looper.getMainLooper()).postDelayed({
                startParticleAnimationWithExistingFiles(particle, index)
            }, (index * 100L))
        }
    }

    private fun startParticleAnimationWithExistingFiles(particle: ImageView, index: Int) {
        when (index % 3) {
            0 -> particle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.particle_float))
            1 -> particle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.float_up_down))
            2 -> particle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse_animation))
        }

        val rotation = ObjectAnimator.ofFloat(particle, "rotation", 0f, 360f).apply {
            duration = (8000 + index * 500).toLong()
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            startDelay = (index * 200L)
        }
        rotation.start()

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
        val gradientRotation1 =
            ObjectAnimator.ofFloat(binding.backgroundGradient1, "rotation", 0f, 360f).apply {
                duration = 15000; repeatCount = ObjectAnimator.INFINITE; interpolator = LinearInterpolator()
            }

        val gradientScale1 =
            ObjectAnimator.ofFloat(binding.backgroundGradient1, "scaleX", 1f, 1.1f, 0.95f, 1f).apply {
                duration = 8000; repeatCount = ObjectAnimator.INFINITE; interpolator = AccelerateDecelerateInterpolator()
            }

        val gradientScaleY1 =
            ObjectAnimator.ofFloat(binding.backgroundGradient1, "scaleY", 1f, 1.1f, 0.95f, 1f).apply {
                duration = 8000; repeatCount = ObjectAnimator.INFINITE; interpolator = AccelerateDecelerateInterpolator()
            }

        val gradientRotation2 =
            ObjectAnimator.ofFloat(binding.backgroundGradient2, "rotation", 360f, 0f).apply {
                duration = 20000; repeatCount = ObjectAnimator.INFINITE; interpolator = LinearInterpolator()
            }

        val gradientScale2 =
            ObjectAnimator.ofFloat(binding.backgroundGradient2, "scaleX", 1f, 0.9f, 1.15f, 1f).apply {
                duration = 6000; repeatCount = ObjectAnimator.INFINITE; interpolator = AccelerateDecelerateInterpolator(); startDelay = 2000
            }

        val gradientScaleY2 =
            ObjectAnimator.ofFloat(binding.backgroundGradient2, "scaleY", 1f, 0.9f, 1.15f, 1f).apply {
                duration = 6000; repeatCount = ObjectAnimator.INFINITE; interpolator = AccelerateDecelerateInterpolator(); startDelay = 2000
            }

        gradientRotation1.start(); gradientScale1.start(); gradientScaleY1.start()
        gradientRotation2.start(); gradientScale2.start(); gradientScaleY2.start()
    }

    // Validation
    private fun setupValidationObservers() {
        viewModel.usernameError.observe(this) { binding.usernameInputLayout.error = it }
        viewModel.displayNameError.observe(this) { binding.displayNameInputLayout.error = it }
        viewModel.emailError.observe(this) { binding.emailInputLayout.error = it }
        viewModel.passwordError.observe(this) { binding.passwordInputLayout.error = it }
        viewModel.confirmPasswordError.observe(this) { binding.confirmPasswordInputLayout.error = it }
    }

    private fun setupTextWatchers() {
        binding.usernameEditText.addTextChangedListener { viewModel.validateUsername(it.toString()) }
        binding.displayNameEditText.addTextChangedListener { viewModel.validateDisplayName(it.toString()) }
        binding.emailEditText.addTextChangedListener { viewModel.validateEmail(it.toString()) }
        binding.passwordEditText.addTextChangedListener { viewModel.validatePassword(it.toString()) }
        binding.confirmPasswordEditText.addTextChangedListener {
            viewModel.validateConfirmPassword(
                binding.passwordEditText.text.toString(),
                it.toString()
            )
        }
    }

    // Clicks
    private fun setupClickListeners() {
        binding.signUpButton.setOnClickListener {
            // Button tap micro-animation
            val scaleDown = ObjectAnimator.ofFloat(it, "scaleX", 1f, 0.95f)
            val scaleDownY = ObjectAnimator.ofFloat(it, "scaleY", 1f, 0.95f)
            val scaleUp = ObjectAnimator.ofFloat(it, "scaleX", 0.95f, 1f)
            val scaleUpY = ObjectAnimator.ofFloat(it, "scaleY", 0.95f, 1f)
            AnimatorSet().apply {
                play(scaleDown).with(scaleDownY); play(scaleUp).with(scaleUpY).after(scaleDown)
                duration = 100; start()
            }

            // Gather inputs
            val username = binding.usernameEditText.text.toString().trim()
            val displayName = binding.displayNameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            // Trigger final validation in VM
            viewModel.validateUsername(username)
            viewModel.validateDisplayName(displayName)
            viewModel.validateEmail(email)
            viewModel.validatePassword(password)
            viewModel.validateConfirmPassword(password, confirmPassword)

            // Proceed only if all errors are null
            if (binding.usernameInputLayout.error == null &&
                binding.displayNameInputLayout.error == null &&
                binding.emailInputLayout.error == null &&
                binding.passwordInputLayout.error == null &&
                binding.confirmPasswordInputLayout.error == null
            ) {
                // TODO: viewModel.signup(username, displayName, email, password)
            }
        }

        binding.loginLinkText.setOnClickListener {
            val scaleX = ObjectAnimator.ofFloat(it, "scaleX", 1f, 1.1f, 1f)
            val scaleY = ObjectAnimator.ofFloat(it, "scaleY", 1f, 1.1f, 1f)
            AnimatorSet().apply { playTogether(scaleX, scaleY); duration = 200; start() }
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}
