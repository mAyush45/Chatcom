package com.example.chatcom

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chatcom.Models.Users
import com.example.chatcom.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase



class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var progressDialog: ProgressDialog
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        val uid = auth.currentUser?.uid
        progressDialog = ProgressDialog(this@SignUpActivity)
        progressDialog.setTitle("Creating Account")
        progressDialog.setMessage("Your account is being created")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.google.setOnClickListener(View.OnClickListener {

            signInGoogle()


        })

        binding.signup.setOnClickListener(View.OnClickListener {

            progressDialog.show()
            auth.createUserWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            ).addOnCompleteListener(
                OnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Account Created Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val i = Intent(this@SignUpActivity, NavigationActivity::class.java)
                        startActivity(i)
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
            val users = Users(username, email, password)
            database.child(username).setValue(users).addOnSuccessListener {
                binding.username.text.clear()
                binding.email.text.clear()
                binding.password.text.clear()
                Toast.makeText(this@SignUpActivity, "User added Successfully", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this@SignUpActivity, "Failed to add User", Toast.LENGTH_SHORT).show()
            }


        })

        binding.alreadyAccount.setOnClickListener(View.OnClickListener {
            val i = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(i)
        })

        binding.signphone.setOnClickListener(View.OnClickListener {
            val i1 = Intent(this@SignUpActivity,PhoneLogin::class.java)
            startActivity(i1)
        })


    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {

        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateUI(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, NavigationActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
}
