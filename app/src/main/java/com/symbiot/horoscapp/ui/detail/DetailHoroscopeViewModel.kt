package com.symbiot.horoscapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.symbiot.horoscapp.domain.model.HoroscopeModel
import com.symbiot.horoscapp.domain.usecase.GetPrediction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailHoroscopeViewModel @Inject constructor(private val getPrediction: GetPrediction) :
    ViewModel() {

    private var _state = MutableStateFlow<DetailHoroscopeState>(DetailHoroscopeState.Loading)
    val state: StateFlow<DetailHoroscopeState> = _state

    lateinit var horoscope: HoroscopeModel
    fun getHoroscope(sign: HoroscopeModel) {
        horoscope = sign
        viewModelScope.launch {
            _state.value = DetailHoroscopeState.Loading
            val result = withContext(Dispatchers.IO) { getPrediction(sign.name) }
            if (result != null) {
                _state.value =
                    DetailHoroscopeState.Success(result.horoscope, result.sign, horoscope)
            } else {
                _state.value =
                    DetailHoroscopeState.Error("Ha ocurrido un error, intentelo mas tarde ")
            }
        }
    }

}