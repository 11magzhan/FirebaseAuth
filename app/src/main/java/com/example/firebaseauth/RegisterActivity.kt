package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.example.firebaseauth.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvLogin.setOnClickListener {
            onBackPressed()
//            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            when{
                TextUtils.isEmpty(binding.etRegisterEmail.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(binding.etRegisterPassword.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = binding.etRegisterEmail.text.toString().trim { it <= ' '}
                    val password: String = binding.etRegisterPassword.text.toString().trim { it <= ' '}
                    // create an instance and create a register a user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                           OnCompleteListener<AuthResult> {task ->
                                if (task.isSuccessful) {
                                    // firebase register user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this, "You are registered successfully", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
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