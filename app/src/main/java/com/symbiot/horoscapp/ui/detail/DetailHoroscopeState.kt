package com.symbiot.horoscapp.ui.detail

import com.symbiot.horoscapp.domain.model.HoroscopeModel

sealed class DetailHoroscopeState {
    data object Loading : DetailHoroscopeState()
    data class Error(val error: String) : DetailHoroscopeState()
    data class Success(val prediction:String, val sign: String, val horoscopeModel: HoroscopeModel) : DetailHoroscopeState()
}