package com.gooddayjobber.repository



interface GoodDayRepository {
    suspend fun getSozoResults(lang: String = "ru")
    suspend fun registerHoiNumber(number: String, registration: Boolean)
}