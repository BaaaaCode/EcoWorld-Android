package com.example.ecoworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnChatbot.setOnClickListener {
            val intent = Intent(this, ChatBotActivity::class.java)
            startActivity(intent)
        }

        binding.btnMission.setOnClickListener {
            val intent = Intent(this, MissionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // ✅ Gemini API 호출 함수 (테스트용)
    private fun callGeminiAPI() {
        val apiKey = BuildConfig.GEMINI_API_KEY
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
