package com.gooddayjobber.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class GooddayAPI {
    companion object {
        const val HOI_BASE_URL = "https://hoi.fm/"
        const val SOZO_BASE_URL = "https://www.soso.kz/"

        //API KEY SHOULD NOT BE IN THE CODE
        const val API_KEY = "M2Y1MjUzOWMtODRmNy00NjVmLWEwN2UtNTNkZWY2ZDE1MGNm"
        const val USER_TOKEN_KEY = "51GhTMJJqVjcVcGuWsbXUKfSiorZF9"
        const val PROJECT_TOKEN_KEY = "6FRlpgn0v1w9NevQsdBC2qrIBvkK0a"

        const val API_KEY_QUERY = "apikey"
        const val USER_TOKEN_QUERY = "usertoken"
        const val PROJECT_TOKEN_QUERY = "projecttoken"
    }

    private val hoiClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.request().newBuilder()
                .addHeader(USER_TOKEN_QUERY, USER_TOKEN_KEY)
                .addHeader(PROJECT_TOKEN_QUERY, PROJECT_TOKEN_KEY)
                .addHeader("Content-Type", "application/json")
                .build()
                .let(chain::proceed).also {
                    Timber.d("response: $it")
                }
        }.build()


//        .apply {
//            addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader(USER_TOKEN_QUERY, USER_TOKEN_KEY)
//                    .addHeader(PROJECT_TOKEN_QUERY, PROJECT_TOKEN_KEY)
//                    .addHeader("Content-Type", "application/json")
//                    .build()
//
//                Timber.d("call: $request")
//                chain.proceed(request).also {
//                    Timber.d("response: $it")
//                }
//            }
//        }.build()

    private val sozoClient = OkHttpClient.Builder()
        .apply {
            addInterceptor { chain ->
                val request = chain.request()

                chain.proceed(
                    request
                        .newBuilder()
                        .url(
                            request.url()
                                .newBuilder()
                                .addQueryParameter(API_KEY_QUERY, API_KEY)
                                .build()
                        )
                        .build()
                ).also {
                    Timber.d("call: $it")
                }
            }
        }.build()

    private fun getRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    fun <T> buildHoiService(service: Class<T>): T = getRetrofit(HOI_BASE_URL, hoiClient).create(service)
    fun <T> buildSozoService(service: Class<T>): T = getRetrofit(SOZO_BASE_URL, sozoClient).create(service)

}