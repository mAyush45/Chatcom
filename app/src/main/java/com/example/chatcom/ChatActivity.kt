package com.example.chatcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatcom.Adapter.ChatAdapter
import com.example.chatcom.Models.Chat
import com.example.chatcom.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.ArrayList
import java.util.HashMap


class ChatActivity : AppCompatActivity() {

    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = ArrayList<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        chatRecyclerView.layoutManager = LinearLayoutManager(this,  RecyclerView.VERTICAL, false)
        var intent = getIntent()
        var userId = intent.getStringExtra("userId")

        imgBack.setOnClickListener {
            onBackPressed()
        }
        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("UsersChat").child(userId!!)


        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                tvUserName.text = user!!.userName
                if (user!!.profileImage == "") {
                    imgProfile.setImageResource(R.drawable.ic_launcher_foreground)
                } else {
                    Glide.with(this@ChatActivity).load(user.profileImage).into(imgProfile)
                }

            }

        })

        btnSendMessage.setOnClickListener {
            var message: String = etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "Message is empty", Toast.LENGTH_SHORT).show()
                etMessage.setText("")
            } else {
                sendMessage(firebaseUser!!.uid, userId, message)
                etMessage.setText("")
            }
        }

        readMessage(firebaseUser!!.uid, userId)
    }




    private fun sendMessage(senderId:String, receiverId:String,message: String){
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap:HashMap<String,String> = HashMap()
        hashMap.put("senderId",senderId)
        hashMap.put("receiverId",receiverId)
        hashMap.put("Message", message)

        reference!!.child("Chat").push().setValue(hashMap)


    }

    fun readMessage(senderId: String,receiverId: String) {

        var databaseReference = FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)

                    if (chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId) ||
                        chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId)
                    ) {
                        chatList.add(chat)
                    }
                }

                val chatAdapter = ChatAdapter(this@ChatActivity, chatList)
                chatRecyclerView.adapter = chatAdapter
            }
        })
    }


}











