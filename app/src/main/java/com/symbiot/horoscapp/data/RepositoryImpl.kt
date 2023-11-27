package com.symbiot.horoscapp.data

import android.util.Log
import com.symbiot.horoscapp.data.network.HoroscopeApiService
import com.symbiot.horoscapp.domain.Repository
import com.symbiot.horoscapp.domain.model.PredictionModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService) : Repository {

    override suspend fun getPrediction(sign: String): PredictionModel? {
        runCatching {
            apiService.getHoroscope(sign)
        }.onSuccess {
            return it.toDomain()
        }.onFailure {
            Log.i("Dev", "Ha ocurrido un error ${it.message}")
        }
        return null
    }

}