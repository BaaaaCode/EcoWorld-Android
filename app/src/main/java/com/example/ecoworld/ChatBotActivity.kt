package com.example.ecoworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoworld.api.*
import com.example.ecoworld.databinding.ActivityChatBotBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatAdapter = ChatAdapter(messages)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatBotActivity)
            adapter = chatAdapter
        }

        binding.btnSend.setOnClickListener {
            val userMessage = binding.editMessage.text.toString()
            if (userMessage.isNotBlank()) {
                addMessage(ChatMessage(userMessage, isUser = true))
                binding.editMessage.text.clear()

                callGeminiAPI(userMessage)  // ✅ Gemini API 호출
            }
        }
    }

    private fun addMessage(message: ChatMessage) {
        messages.add(message)
        chatAdapter.notifyItemInserted(messages.size - 1)
        binding.recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun callGeminiAPI(prompt: String) {
        val apiKey = BuildConfig.GEMINI_API_KEY  // ✅ build.gradle.kts에 반드시 API Key 정의 필요!
        val apiService = RetrofitClient.getClient(apiKey)

        val request = GeminiRequest(
            contents = listOf(
                GeminiContent(
                    parts = listOf(
                        GeminiPart(prompt)
                    )
                )
            )
        )

        apiService.askGemini(request).enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                if (response.isSuccessful) {
                    val answer = response.body()?.candidates?.getOrNull(0)?.get("content")
                    addMessage(ChatMessage(answer ?: "답변 없음", isUser = false))
                } else {
                    addMessage(ChatMessage("API 호출 실패: ${response.code()}", isUser = false))
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                addMessage(ChatMessage("API 호출 에러: ${t.localizedMessage}", isUser = false))
            }
        })
    }
}
