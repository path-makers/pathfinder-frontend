package com.example.pathfinder.ui.userProfile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentUserProfileBinding
import com.example.pathfinder.ui.auth.IntroActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class ModifyActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var userImage: ImageView
    private val GALLERY_REQUEST_CODE = 1
    private val UCROP_REQUEST_CODE = 2
    private var imageUri: Uri? = null


    // 갤러리 열기
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            }
        }
    // 가져온 사진 보여주기
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    imageUri = it
                    userImage.setImageURI(imageUri)
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        auth = Firebase.auth

        val user = Firebase.auth.currentUser

        val backBtn: Button = findViewById(R.id.button_back)
        val completeBtn: TextView = findViewById(R.id.btn_complete)
        userImage = findViewById(R.id.userImage)
        val userImage2: ImageView = findViewById(R.id.userImage2)
        val btnChangeImage: ImageButton = findViewById(R.id.changeImage)
        val etUpdateName: EditText = findViewById(R.id.updateName)
        val etUpdateEmail: EditText = findViewById(R.id.updateEmail)
        val etUpdatePassword: EditText = findViewById(R.id.updatePassword)

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Update the views
            // For example:
            Glide.with(this)
                .load(photoUrl).circleCrop()
                .into(userImage)
            etUpdateName.setText(name)
            etUpdateEmail.setText(email)
        }


        backBtn.setOnClickListener() {
            clearBackStack()
        }

        completeBtn.setOnClickListener {
            var updatesCount = 0
            var completedUpdates = 0

            fun checkCompletion() {
                completedUpdates++
                if (completedUpdates == updatesCount) {
                    val resultIntent = Intent()
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish() // 모든 업데이트가 완료되면 액티비티 종료
                }
            }

            // username 변경
            val newUserName = etUpdateName.text.toString()
            if (newUserName.isNotEmpty()) {
                updatesCount++
                val profileUpdates = userProfileChangeRequest {
                    displayName = newUserName
                }

                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener {
                        checkCompletion()
                    }
            }

            // email 변경
            val newEmail = etUpdateEmail.text.toString()
            if (newEmail.isNotEmpty()) {
                updatesCount++
                user!!.updateEmail(newEmail)
                    .addOnCompleteListener {
                        checkCompletion()
                    }
            }

            // password 변경
            val newPassword = etUpdatePassword.text.toString()
            if (newPassword.isNotEmpty()) {
                updatesCount++
                user!!.updatePassword(newPassword)
                    .addOnCompleteListener {
                        checkCompletion()
                    }
            }

            if (updatesCount == 0) {
                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()// 변경할 항목이 없으면 바로 종료
            }
        }

        // 사진 변경
        userImage.clipToOutline = true
        btnChangeImage.setOnClickListener() {
            openGallery()
            userImage2.setImageDrawable(null)
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                openGallery()
//            } else {
//                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//            }
        }




    }

    private fun clearBackStack() {
        super.onBackPressed()
        return
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
    }

}