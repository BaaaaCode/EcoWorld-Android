package com.example.ecoworld.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor  // ✅ 로그 인터셉터 import

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    fun getClient(apiKey: String): GeminiApiService {
        // ✅ 여기서 logging 변수 선언!
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // 전체 요청/응답 Body까지 로그 출력
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Goog-Api-Key", apiKey)
                    .build()
                chain.proceed(request)
            })
            .addInterceptor(logging) // ✅ 여기서 선언한 logging 사용
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApiService::class.java)
    }
}
