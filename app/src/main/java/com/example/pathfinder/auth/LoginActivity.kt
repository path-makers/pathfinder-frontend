package com.example.pathfinder.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.MainActivity
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailArea.text.toString()
            val pwd = binding.pwdArea.text.toString()

            auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, display a message to the user.
//                    Toast.makeText(baseContext, "Authentication succeeded.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "auth failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}