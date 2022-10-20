package com.example.chatcom.profiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.chatcom.R

class MyProfileActivity : AppCompatActivity() {

    private lateinit var bindingMyProfileActivity: MyProfileActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)


        initprofile()
    }

    private fun initprofile() {

        findViewById<ImageView>(R.id.myprofile1).setOnClickListener{

        }

    }

}

