package com.example.ecoworld

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoworld.databinding.ActivityChatBotBinding

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ 이미지 업로드 버튼 클릭
        binding.btnUploadImage.setOnClickListener {
            dispatchTakePictureIntent()
        }

        // ✅ 질문 제출 버튼 클릭 (더미 응답 처리)
        binding.btnSubmit.setOnClickListener {
            val question = binding.etQuestion.text.toString()
            if (question.isNotBlank()) {
                binding.tvResult.text = "[더미 응답] 재활용 가능합니다!"
            } else {
                binding.tvResult.text = "질문을 입력하세요."
            }
        }
    }

    // ✅ 카메라 앱 호출
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // ✅ 촬영 후 이미지 세팅
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.ivUploadImage.setImageBitmap(imageBitmap)
        }
    }
}
