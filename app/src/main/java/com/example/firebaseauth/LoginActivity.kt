package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.firebaseauth.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            when{
                TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = binding.etLoginEmail.text.toString().trim { it <= ' '}
                    val password: String = binding.etLoginPassword.text.toString().trim { it <= ' '}
                    // Login using FirebaseAuth
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    // firebase register user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this, "You are login successfully", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            })
                }
            }

        }


    }
}