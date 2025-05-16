package com.example.ecoworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
            startActivity(Intent(this, ChatBotActivity::class.java))
        }

        binding.btnMission.setOnClickListener {
            startActivity(Intent(this, MissionActivity::class.java))
        }

        binding.btnTimeline.setOnClickListener {
            val intent = Intent(this, PointTimelineActivity::class.java)
            startActivity(intent)
        }

        // ✅ 테스트로 앱 시작 시 API 호출
        callGeminiAPI()
    }

    private fun callGeminiAPI() {
        val apiKey = BuildConfig.GEMINI_API_KEY
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

        // 로딩 시작
        binding.progressBar.visibility = View.VISIBLE
        binding.cardResult.visibility = View.GONE

        apiService.askGemini(request).enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                binding.progressBar.visibility = View.GONE
                binding.cardResult.visibility = View.VISIBLE

                if (response.isSuccessful) {
                    val answer = response.body()?.candidates?.getOrNull(0)?.get("content")
                    binding.testText.text = answer ?: "답변이 없습니다."
                } else {
                    binding.testText.text = "API 호출 실패: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                binding.cardResult.visibility = View.VISIBLE
                binding.testText.text = "API 호출 에러: ${t.localizedMessage}"
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}