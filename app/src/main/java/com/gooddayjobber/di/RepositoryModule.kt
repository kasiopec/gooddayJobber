package com.gooddayjobber.di

import com.gooddayjobber.repository.GoodDayRepository
import com.gooddayjobber.repository.GoodDayRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun goodDayRepository(repository: GoodDayRepositoryImpl): GoodDayRepository
}