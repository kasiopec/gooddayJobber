package com.gooddayjobber.di

import com.gooddayjobber.network.GooddayAPI
import com.gooddayjobber.network.HoiService
import com.gooddayjobber.network.SozoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    private val api: GooddayAPI = GooddayAPI()

    @Singleton
    @Provides
    fun hoiApi(): HoiService = api.buildHoiService(HoiService::class.java)

    @Singleton
    @Provides
    fun sozoApi(): SozoService = api.buildSozoService(SozoService::class.java)
}