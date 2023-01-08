package com.gooddayjobber.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gooddayjobber.repository.GoodDayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FirstFragViewModel @Inject constructor(
    private val repository: GoodDayRepository
) : ViewModel() {


    fun registerNumber(string: String){
        viewModelScope.launch {
            repository.registerHoiNumber(number = string, registration = true)
        }
    }

    fun getSozoData() {
        viewModelScope.launch {
            try{
                repository.getSozoResults()
            } catch (e: HttpException) {
                //
            } catch (e: Throwable) {
                //
            }
        }
    }
}