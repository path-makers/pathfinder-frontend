package com.example.mysolelife.utils

import com.example.mysolelife.auth.JoinActivity
import com.example.mysolelife.databinding.ActivityJoinBinding
import com.example.mysolelife.databinding.FragmentStoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
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
        fun getEmail() : String{

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.email.toString()
        }

        fun getUserName() : String{
            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.displayName.toString()
        }



        fun getTime(): String{

            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)

            return dateFormat
        }
    }
}