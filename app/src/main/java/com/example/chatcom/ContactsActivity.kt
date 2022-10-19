package com.example.chatcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactsActivity : AppCompatActivity() {
     protected lateinit var fabAdd: FloatingActionButton
    protected lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        val toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)



    }
}