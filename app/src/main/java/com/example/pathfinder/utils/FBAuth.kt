package com.example.pathfinder.utils

import com.example.pathfinder.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {

    companion object{

        private lateinit var binding: ActivityJoinBinding
        private lateinit var  auth: FirebaseAuth

        fun getUid() : String{

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()

        }

        fun getUserName() : String{
            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.displayName.toString()
        }


        fun getTime(): String {

            val currentDateTime = Calendar.getInstance().time

            return SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)
        }
    }
}