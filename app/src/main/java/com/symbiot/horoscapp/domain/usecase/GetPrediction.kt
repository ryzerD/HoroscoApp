package com.symbiot.horoscapp.domain.usecase

import com.symbiot.horoscapp.domain.Repository
import javax.inject.Inject

class GetPrediction @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(sing: String)  = repository.getPrediction(sing)

}