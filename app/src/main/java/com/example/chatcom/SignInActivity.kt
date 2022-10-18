package com.example.chatcom

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chatcom.databinding.ActivitySignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this@SignInActivity)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Login to your account")

        binding.signin.setOnClickListener(View.OnClickListener{

            progressDialog.show()
            auth.signInWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString())
                .addOnCompleteListener(OnCompleteListener{
                    progressDialog.dismiss()
                    if(it.isSuccessful){
                        val i = Intent(this@SignInActivity,MainActivity::class.java)
                        startActivity(i)
                    }
                    else{
                        Toast.makeText(this,"Couldn't sign in",Toast.LENGTH_SHORT).show()
                    }
                })


        })

        if(auth.currentUser != null){
            val i1 = Intent(this@SignInActivity,MainActivity::class.java)
            startActivity(i1)
        }
    }
}