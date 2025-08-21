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
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.example.spendwise.MainActivity
import com.example.spendwise.R
import com.example.spendwise.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    companion object {
        private const val SPLASH_DISPLAY_LENGTH = 3000L // 3 seconds
        private const val ANIMATION_DELAY = 500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Make status bar transparent
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )

        // Start animations
        startAnimations()

        // Navigate after delay
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, SPLASH_DISPLAY_LENGTH)
    }

    private fun startAnimations() {
        // Start ring rotations
        startRingAnimations()

        // Start logo animation
        startLogoAnimation()

        // Start text animations
        startTextAnimations()

        // Start particle animations
        startParticleAnimations()
    }

    private fun startRingAnimations() {
        // Outer ring rotation
        val outerRingRotation = ObjectAnimator.ofFloat(binding.outerRing, "rotation", 0f, 360f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = DecelerateInterpolator()
        }

        // Middle ring counter rotation
        val middleRingRotation = ObjectAnimator.ofFloat(binding.middleRing, "rotation", 360f, 0f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
        }

        outerRingRotation.start()
        middleRingRotation.start()
    }

    private fun startLogoAnimation() {
        // Logo scale and fade in
        binding.logoCard.apply {
            scaleX = 0.5f
            scaleY = 0.5f
            alpha = 0f
        }

        val scaleX = ObjectAnimator.ofFloat(binding.logoCard, "scaleX", 0.5f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(binding.logoCard, "scaleY", 0.5f, 1.0f)
        val fadeIn = ObjectAnimator.ofFloat(binding.logoCard, "alpha", 0f, 1f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, fadeIn)
            duration = 1000
            interpolator = DecelerateInterpolator()
            startDelay = ANIMATION_DELAY
            start()
        }

        // Logo pulse animation
        Handler(Looper.getMainLooper()).postDelayed({
            val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation)
            binding.logoCard.startAnimation(pulseAnimation)
        }, ANIMATION_DELAY + 1000)
    }

    private fun startTextAnimations() {
        // App name animation
        Handler(Looper.getMainLooper()).postDelayed({
            binding.appName.apply {
                translationY = 100f
                alpha = 0f
                visibility = View.VISIBLE
            }

            val translateY = ObjectAnimator.ofFloat(binding.appName, "translationY", 100f, 0f)
            val fadeIn = ObjectAnimator.ofFloat(binding.appName, "alpha", 0f, 1f)

            AnimatorSet().apply {
                playTogether(translateY, fadeIn)
                duration = 800
                interpolator = DecelerateInterpolator()
                start()
            }
        }, ANIMATION_DELAY + 600)

        // Tagline animation
        Handler(Looper.getMainLooper()).postDelayed({
            binding.tagline.apply {
                translationY = 50f
                alpha = 0f
                visibility = View.VISIBLE
            }

            val translateY = ObjectAnimator.ofFloat(binding.tagline, "translationY", 50f, 0f)
            val fadeIn = ObjectAnimator.ofFloat(binding.tagline, "alpha", 0f, 0.8f)

            AnimatorSet().apply {
                playTogether(translateY, fadeIn)
                duration = 600
                interpolator = DecelerateInterpolator()
                start()
            }
        }, ANIMATION_DELAY + 1000)
    }

    private fun startParticleAnimations() {
        val particles = listOf(
            binding.particle1,
            binding.particle2,
            binding.particle3,
            binding.particle4
        )

        particles.forEachIndexed { index, particle ->
            Handler(Looper.getMainLooper()).postDelayed({
                // Random float animation for each particle
                val translateY = ObjectAnimator.ofFloat(
                    particle,
                    "translationY",
                    0f,
                    -30f - (index * 10f)
                ).apply {
                    duration = 2000 + (index * 500L)
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.REVERSE
                    interpolator = AccelerateDecelerateInterpolator()
                }

                val alpha = ObjectAnimator.ofFloat(
                    particle,
                    "alpha",
                    0.3f,
                    0.8f
                ).apply {
                    duration = 3000 + (index * 300L)
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.REVERSE
                    interpolator = AccelerateDecelerateInterpolator()
                }

                translateY.start()
                alpha.start()
            }, ANIMATION_DELAY + (index * 200L))
        }
    }

    private fun navigateToNextScreen() {
        // Show loading indicator
        binding.loadingIndicator.visibility = View.VISIBLE

        // Modern transition animation - Scale and fade out with overshoot
        val scaleX = ObjectAnimator.ofFloat(binding.root, "scaleX", 1f, 0.9f)
        val scaleY = ObjectAnimator.ofFloat(binding.root, "scaleY", 1f, 0.9f)
        val fadeOut = ObjectAnimator.ofFloat(binding.root, "alpha", 1f, 0f)
        val translateY = ObjectAnimator.ofFloat(binding.root, "translationY", 0f, -50f)

        AnimatorSet().apply {
            playTogether(scaleX, scaleY, fadeOut, translateY)
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            doOnEnd {
                val intent = Intent(this@SplashActivity, SignupActivity::class.java)
                startActivity(intent)
                // Custom transition animations
                overridePendingTransition(R.anim.slide_in_up_scale, R.anim.slide_out_up_fade)
                finish()
            }
            start()
        }
    }

    override fun onBackPressed() {
        // Disable back button on splash screen
        // Do nothing
    }
}