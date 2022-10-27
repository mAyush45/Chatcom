package com.example.chatcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.chatcom.Models.Chat
import com.example.chatcom.Models.User
import com.example.chatcom.databinding.ActivityChatBinding
import com.example.chatcom.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList
import java.util.HashMap


class ChatActivity : AppCompatActivity() {
    var firebaseUser:FirebaseUser? = null
    var reference:DatabaseReference? = null
    var chatList = ArrayList<Chat>()

    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var intent = intent
        var userId = intent.getStringExtra("userId")

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        reference!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                binding.tvName.text = user!!.userName
                if(user!!.profileImage == ""){
                    binding.profile.setImageResource(R.drawable.ic_launcher_background)
                }
                else{
                    Glide.with(this@ChatActivity).load(user.profileImage).into(binding.profile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.btnSend.setOnClickListener{
            var message = binding.tvMessage.text.toString()

            if(message.isEmpty()){
                Toast.makeText(applicationContext,"message is empty",Toast.LENGTH_SHORT).show()
            }
            else{
                 sendMessage(firebaseUser!!.uid,userId, message)
            }
        }

    }

    private fun sendMessage(senderId:String,recieverId:String,message:String){
        var reference:DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap:HashMap<String,String> = HashMap()
        hashMap.put("senderId",senderId)
        hashMap.put("recieverId",recieverId)
        hashMap.put("message",message)

        reference!!.child("Chat").push().setValue(hashMap)
    }
}

