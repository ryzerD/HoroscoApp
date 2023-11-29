package com.symbiot.horoscapp.ui.luck

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.symbiot.horoscapp.R
import com.symbiot.horoscapp.databinding.FragmentLuckBinding
import com.symbiot.horoscapp.ui.core.listener.OnSwipeTouchListener
import com.symbiot.horoscapp.ui.providers.RandomCardProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import javax.inject.Inject

@AndroidEntryPoint
class LuckFragment : Fragment() {
    private var _binding: FragmentLuckBinding? = null
    private val luckViewModel by viewModels<LuckViewModel>()
    private val binding get() = _binding!!

    @Inject
    lateinit var randomCardProvider: RandomCardProvider


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLuckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        preparedPrediction()
        initListeners()
        val network: Boolean = isNetworkAvailable(requireContext())
        if (!network) {
            showNoInternetSnackbar()
        }
    }

    private fun preparedPrediction() {
        val luck = randomCardProvider.getLucky()
        luck?.let {
            val currentPrediction = getString(it.name)
            binding.tvLucky.text = currentPrediction
            binding.ivLuckyCard.setImageResource(it.image)
            binding.tvShare.setOnClickListener { shareResult(currentPrediction) }
        }
    }

    private fun shareResult(luck: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, luck)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        /*  binding.IvRoulette.setOnClickListener {
              spinRoulette()
          }*/
        binding.IvRoulette.setOnTouchListener(object : OnSwipeTouchListener(requireContext()) {
            override fun onSwipeRight() {
                spinRoulette()
            }

            override fun onSwipeLeft() {
                spinRoulette()
            }
        })
    }

    private fun spinRoulette() {
        val random = Random()
        val degress = random.nextInt(1440) + 360
        val animator =
            ObjectAnimator.ofFloat(binding.IvRoulette, View.ROTATION, 0f, degress.toFloat())
        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.doOnEnd { slideCard() }
        animator.start()
    }

    private fun slideCard() {
        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.reverse.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                growCard()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        binding.reverse.startAnimation(slideUpAnimation)
    }

    private fun growCard() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow_up)
        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.reverse.isVisible = false
                showPredictionView()
            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })
        binding.reverse.startAnimation(growAnimation)
    }

    private fun showPredictionView() {
        val disappearAnimation = AlphaAnimation(1.0f, 0.0f)
        val apearAnimation = AlphaAnimation(0.0f, 1.0f)
        apearAnimation.duration = 1000
        disappearAnimation.duration = 200
        disappearAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.preview.isVisible = false
                binding.predict.isVisible = true
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.preview.startAnimation(disappearAnimation)
        binding.predict.startAnimation(apearAnimation)
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showNoInternetSnackbar() {
        val snackbar = Snackbar.make(binding.root, "No hay conexión", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_red_dark
            )
        )
        snackbar.show()
    }


}