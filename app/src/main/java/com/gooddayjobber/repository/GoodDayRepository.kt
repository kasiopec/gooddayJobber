package com.gooddayjobber.repository

import com.gooddayjobber.model.HoiResponse
import com.gooddayjobber.model.SozoResponse
import com.gooddayjobber.network.NetworkResponse
import retrofit2.Response


interface GoodDayRepository {
    suspend fun getSozoResults(lang: String = "ru"): NetworkResponse<List<SozoResponse>>
    suspend fun registerHoiNumber(number: String, registration: Boolean): NetworkResponse<HoiResponse>
}