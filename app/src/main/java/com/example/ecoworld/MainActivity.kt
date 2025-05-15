package com.example.ecoworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoworld.api.*
import com.example.ecoworld.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ [보조 기능] 분리수거 분류 화면 이동 (현재 비활성화, 필요 시 주석 해제)
        /*
        binding.btnCategory.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        */

        // ✅ [주요 기능] 챗봇 테스트 API 호출
        binding.btnChatbot.setOnClickListener {
            val intent = Intent(this, ChatBotActivity::class.java)
            startActivity(intent)
        }
    }

    // ✅ Gemini API 호출 함수 (테스트용)
    private fun callGeminiAPI() {
        val apiKey = BuildConfig.GEMINI_API_KEY  // build.gradle.kts에 선언한 Key 사용
        Log.d("API_KEY_CHECK", "API KEY: $apiKey")

        val apiService = RetrofitClient.getClient(apiKey)

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(
                        GeminiPart("이 플라스틱 컵은 어떻게 버려야 해?")
                    )
                )
            )
        )

        // ✅ JSON 요청 내용 로그 출력
        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.d("FINAL_REQUEST_JSON", gson.toJson(request))

        apiService.askGemini(request).enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                if (response.isSuccessful) {
                    val answer = response.body()?.candidates?.getOrNull(0)?.get("content")
                    binding.testText.text = answer ?: "답변 없음"
                } else {
                    binding.testText.text = "API 호출 실패: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                binding.testText.text = "API 호출 에러: ${t.localizedMessage}"
            }
        })
    }
}
