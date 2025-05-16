package com.example.ecoworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoworld.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 임시 - 설정 화면 텍스트 표시
        binding.tvSettingsInfo.text = "설정 화면입니다. (추후 구현)"
    }
}
