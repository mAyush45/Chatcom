package com.example.chatcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.chatcom.databinding.ActivityAddContactsBinding
import com.example.chatcom.databinding.ActivitySignUpBinding

class AddContactsActivity : AppCompatActivity() {

    private lateinit var edFName : EditText
    private lateinit var edLName : EditText
    private lateinit var edCode : EditText
    private lateinit var edPhone : EditText
    private lateinit var tvCountryCode : TextView



    private lateinit var binding: ActivityAddContactsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAddContactsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        initClick()


    }

    private fun init(){

    }

    private fun initClick() {
        binding.btnBack1.setOnClickListener { view -> finish() }
        binding.btnDone1.setOnClickListener {
            //CHeck Validate
            if(TextUtils.isEmpty(binding.etFirstName.text.toString())){
                binding.etFirstName.error = "Field required !"
            }
            else if(TextUtils.isEmpty(binding.etLastName.text.toString())){
                binding.etFirstName.error = "Field required !"
            }
            else if(TextUtils.isEmpty(binding.etPhno.text.toString())) {
                binding.etPhno.error = "Please enter a phone number !"
            }
            else if(TextUtils.isEmpty(binding.etCc.text.toString())) {
                binding.etFirstName.error = "Field required !"
            }
            else{
                addContact()
            }
        }
    }


    private fun addContact() {



    }
}
