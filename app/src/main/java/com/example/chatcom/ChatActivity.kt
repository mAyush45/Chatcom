package com.example.chatcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatcom.databinding.ActivityChatBinding
import com.example.chatcom.databinding.ActivitySignUpBinding


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }


    }
}

