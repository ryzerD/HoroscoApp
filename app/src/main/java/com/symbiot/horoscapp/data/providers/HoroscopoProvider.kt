package com.symbiot.horoscapp.data.providers

import com.symbiot.horoscapp.domain.model.HoroscopeInfo
import com.symbiot.horoscapp.domain.model.HoroscopeInfo.*
import javax.inject.Inject

class HoroscopoProvider @Inject constructor() {
    fun getHoroscopes(): List<HoroscopeInfo> {
        return listOf(
            Aries,
            Taurus,
            Gemini,
            Cancer,
            Leo,
            Virgo,
            Libra,
            Scorpio,
            Sagittarius,
            Capricorn,
            Aquarius,
            Pisces
        )
    }
}