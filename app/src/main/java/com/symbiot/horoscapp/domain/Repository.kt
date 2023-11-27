package com.symbiot.horoscapp.domain

import com.symbiot.horoscapp.domain.model.PredictionModel

interface Repository {
    suspend fun getPrediction(sign:String):PredictionModel?
}