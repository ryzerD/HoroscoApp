package com.symbiot.horoscapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.symbiot.horoscapp.R
import com.symbiot.horoscapp.databinding.ActivityDetailHoroscopeBinding
import com.symbiot.horoscapp.domain.model.HoroscopeModel
import com.symbiot.horoscapp.domain.model.HoroscopeModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailHoroscopeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHoroscopeBinding
    private val detailHoroscopeViewModel: DetailHoroscopeViewModel by viewModels()
    private val args: DetailHoroscopeActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHoroscopeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailHoroscopeViewModel.getHoroscope(args.type)
        initUI()
    }

    private fun initUI() {
        initListener()
        initUIState()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailHoroscopeViewModel.state.collect() {
                    when (it) {
                        is DetailHoroscopeState.Error -> errorState()
                        DetailHoroscopeState.Loading -> loadingState()
                        is DetailHoroscopeState.Success -> succesState(it)

                    }
                }
            }
        }
    }

    private fun succesState(state: DetailHoroscopeState.Success) {
        binding.pbLoading.isVisible = false
        binding.tvTitle.text = state.sign
        binding.tvBody.text = state.prediction

        val image = when(state.horoscopeModel){
            Aries -> R.drawable.detail_aries
            Taurus -> R.drawable.detail_taurus
            Gemini -> R.drawable.detail_gemini
            Cancer -> R.drawable.detail_cancer
            Leo -> R.drawable.detail_leo
            Virgo -> R.drawable.detail_virgo
            Libra -> R.drawable.detail_libra
            Scorpio -> R.drawable.detail_scorpio
            Sagittarius -> R.drawable.detail_sagittarius
            Capricorn -> R.drawable.detail_capricorn
            Aquarius -> R.drawable.detail_aquarius
            Pisces -> R.drawable.detail_pisces
        }
        binding.ivDetail.setImageResource(image)
    }

    private fun errorState() {
        binding.pbLoading.isVisible = false
    }

    private fun loadingState() {
        binding.pbLoading.isVisible = true
    }
}