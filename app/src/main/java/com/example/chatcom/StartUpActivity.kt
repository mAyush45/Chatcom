package com.example.chatcom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatcom.databinding.ActivitySignUpBinding
import com.example.chatcom.databinding.ActivityStartUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.view.View

class StartUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartUpBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.startEngaging.setOnClickListener {

            if (auth.currentUser != null) {

                val i = Intent(this@StartUpActivity, NavigationActivity::class.java)
                startActivity(i)

            }
            else{

                val i1 = Intent(this@StartUpActivity, SignUpActivity::class.java)
                startActivity(i1)
            }

        }

    }
}