package com.gooddayjobber.repository

import com.gooddayjobber.network.HoiService
import com.gooddayjobber.network.SozoService
import javax.inject.Inject

class GoodDayRepositoryImpl @Inject constructor(
    private val sozoApi: SozoService,
    private val hoiApi: HoiService
) : GoodDayRepository {

    override suspend fun getSozoResults(lang: String) {
        val response = sozoApi.getLenders(lang = lang)
        if(response.isSuccessful){

        }
    }

    override suspend fun registerHoiNumber(number: String, registration: Boolean) {
        hoiApi.registerNumber(
            number = number.toLong(),
            registration = registration
        )
    }
}