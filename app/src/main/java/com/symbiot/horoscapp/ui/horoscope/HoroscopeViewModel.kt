package com.symbiot.horoscapp.ui.horoscope

import androidx.lifecycle.ViewModel
import com.symbiot.horoscapp.data.providers.HoroscopoProvider
import com.symbiot.horoscapp.domain.model.HoroscopeInfo
import com.symbiot.horoscapp.domain.model.HoroscopeInfo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HoroscopeViewModel @Inject constructor(
    provider: HoroscopoProvider
) : ViewModel() {

    private var _horoscope = MutableStateFlow<List<HoroscopeInfo>>(emptyList())
    val horoscope: StateFlow<List<HoroscopeInfo>> = _horoscope

    init {
        _horoscope.value = provider.getHoroscopes()
    }

    fun getHoroscope() {

    }
}