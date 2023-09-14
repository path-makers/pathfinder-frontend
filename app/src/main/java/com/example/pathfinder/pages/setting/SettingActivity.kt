package com.example.pathfinder.pages.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pathfinder.R
import com.example.pathfinder.R.id
import com.example.pathfinder.auth.IntroActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        auth = Firebase.auth

        val logoutBtn:Button = findViewById(id.logoutBtn)
        logoutBtn.setOnClickListener {

            auth.signOut()

//            Toast.makeText(this,"로그아웃",Toast.LENGTH_LONG).show()

            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)


        }
    }
}