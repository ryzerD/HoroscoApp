package com.symbiot.horoscapp.data.network

import com.symbiot.horoscapp.data.network.Response.PredictionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HoroscopeApiService {
    @GET("/{sign}")
    suspend fun getHoroscope(@Path("sign") sign: String): PredictionResponse

}