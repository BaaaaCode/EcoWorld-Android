package com.example.ecoworld.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GeminiApiService {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-1.5-pro:generateContent")
    fun askGemini(
        @Body body: GeminiRequest
    ): Call<GeminiResponse>
}
