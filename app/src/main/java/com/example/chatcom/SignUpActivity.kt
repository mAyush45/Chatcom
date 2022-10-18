package com.example.chatcom

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.chatcom.Models.Users
import com.example.chatcom.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this@SignUpActivity)
        progressDialog.setTitle("Creating Account")
        progressDialog.setMessage("Your account is being created")

        binding.signup.setOnClickListener(View.OnClickListener {

            progressDialog.show()
            auth.createUserWithEmailAndPassword(binding.email.text.toString(),binding.password.text.toString()).addOnCompleteListener(
                OnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Account Created Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Failed to create Account",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val username = binding.username.text.toString()
            database = FirebaseDatabase.getInstance().getReference("Users")
            val users = Users(username,email,password)
            database.child(username).setValue(users).addOnSuccessListener {
                binding.username.text.clear()
                binding.email.text.clear()
                binding.password.text.clear()
                Toast.makeText(this@SignUpActivity,"User added Successfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@SignUpActivity,"Failed to add User",Toast.LENGTH_SHORT).show()
            }


        })



    }
}

