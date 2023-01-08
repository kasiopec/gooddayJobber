package com.gooddayjobber.network

import com.gooddayjobber.model.SozoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SozoService {

    @GET("api/lenders")
    suspend fun getLenders(
        @Query("lang") lang: String,
    ): Response<List<SozoResponse>>

}