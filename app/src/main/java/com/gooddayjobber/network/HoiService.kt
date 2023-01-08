package com.gooddayjobber.network

import com.gooddayjobber.model.HoiResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface HoiService {

    @POST("api/sms")
    suspend fun registerNumber(
        @Query("number") number: Long,
        @Query("registration") registration: Boolean,
    ): Response<HoiResponse>

}