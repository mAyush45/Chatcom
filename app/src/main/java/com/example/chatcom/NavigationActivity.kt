package com.example.chatcom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatcom.Adapter.UserAdapter
import com.example.chatcom.Models.User
import com.example.chatcom.databinding.ActivityNavigationBinding
import com.example.chatcom.profiles.MyProfileActivity
import com.google.android.datatransport.runtime.logging.Logging.d

import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView


class NavigationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding

    private var userList=ArrayList<User>()


    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        draw()

        val userRecyclerView : RecyclerView= findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.navView.getHeaderView(0).findViewById<CircleImageView>(R.id.myprofile1).setOnClickListener{
            startActivity(Intent(this,MyProfileActivity::class.java))
        }
        userList.add(User())
        val firebase:FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var databaseReference =  FirebaseDatabase.getInstance().getReference("UserChat")
        databaseReference.addValueEventListener(object :ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val user = dataSnapShot.getValue(User::class.java)

                    if (!user!!.userId.equals(firebase.uid)) {
                        userList.add(user)
                    }
                }

                val userAdapter = UserAdapter(this@NavigationActivity, userList)
                userRecyclerView.adapter = userAdapter


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                TODO("Not yet implemented")
            }
        })



    }






    private fun draw(){
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.contacts->{
                    val i = Intent(this, ContactsActivity::class.java)
                    startActivity(i)
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item)
    }
}
