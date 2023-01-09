package com.gooddayjobber.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooddayjobber.model.HoiResponse
import com.gooddayjobber.model.SozoResponse
import com.gooddayjobber.network.NetworkResponse
import com.gooddayjobber.repository.GoodDayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstFragViewModel @Inject constructor(
    private val repository: GoodDayRepository
) : ViewModel() {

    private val hoiData_ = MutableStateFlow(HoiResponse("initial", 0))
    val hoiData: StateFlow<HoiResponse> = hoiData_
    private val hoiErrorChannel = Channel<String>()
    val hoiErrorFlow = hoiErrorChannel.receiveAsFlow()

    private val sozoData_ = MutableStateFlow<List<SozoResponse>>(emptyList())
    val sozoData: StateFlow<List<SozoResponse>> = sozoData_
    private val sozoErrorChannel = Channel<String>()
    val sozoErrorFlow = sozoErrorChannel.receiveAsFlow()


    fun registerNumber(string: String) {
        viewModelScope.launch {
            when (val result = repository.registerHoiNumber(number = string, registration = true)) {
                is NetworkResponse.Error -> hoiErrorChannel.send("Error: ${result.code} ${result.message}")
                is NetworkResponse.Exception -> hoiErrorChannel.send("Exception: ${result.ex.message}")
                is NetworkResponse.Success -> hoiData_.emit(result.data)
            }
        }
    }

    fun getSozoData() {
        viewModelScope.launch {
            when (val result = repository.getSozoResults()) {
                is NetworkResponse.Error -> sozoErrorChannel.send("Error: ${result.code} ${result.message}")
                is NetworkResponse.Exception -> sozoErrorChannel.send("Exception: ${result.ex.message}")
                is NetworkResponse.Success -> sozoData_.emit(result.data)
            }
        }
    }
}