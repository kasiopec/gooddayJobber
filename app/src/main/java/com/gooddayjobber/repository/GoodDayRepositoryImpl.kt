package com.gooddayjobber.repository

import com.gooddayjobber.model.HoiResponse
import com.gooddayjobber.model.SozoResponse
import com.gooddayjobber.network.HoiService
import com.gooddayjobber.network.NetworkResponse
import com.gooddayjobber.network.SozoService
import com.gooddayjobber.network.handleApi
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class GoodDayRepositoryImpl @Inject constructor(
    private val sozoApi: SozoService,
    private val hoiApi: HoiService
) : GoodDayRepository {

    override suspend fun getSozoResults(lang: String): NetworkResponse<List<SozoResponse>> {
        return handleApi {
            sozoApi.getLenders(lang = lang)
        }

    }

    override suspend fun registerHoiNumber(number: String, registration: Boolean): NetworkResponse<HoiResponse> {
        return handleApi {
            hoiApi.registerNumber(number = number, registration = registration)
        }
    }
}