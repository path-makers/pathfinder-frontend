package com.example.pathfinder.ui.userProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pathfinder.databinding.ActivityModifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class EditUserProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityModifyBinding

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                binding.userImage.setImageURI(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        setupUI()
    }

    private fun setupUI() {
        auth.currentUser?.let { user ->
            Glide.with(this).load(user.photoUrl).circleCrop().into(binding.userImage)
            binding.updateName.setText(user.displayName)
            binding.updateEmail.setText(user.email)
        }

        binding.buttonBack.setOnClickListener { onBackPressed() }
        binding.btnComplete.setOnClickListener { updateProfile() }
        binding.changeImage.setOnClickListener { openGallery() }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(galleryIntent)
    }

    private fun updateProfile() {
        val user = auth.currentUser ?: return

        user.updateProfile(userProfileChangeRequest {
            displayName = binding.updateName.text.toString().takeIf { it.isNotEmpty() }
        }).addOnCompleteListener { showUpdateResult(it.isSuccessful) }

        user.updateEmail(binding.updateEmail.text.toString()).addOnCompleteListener { showUpdateResult(it.isSuccessful) }

        user.updatePassword(binding.updatePassword.text.toString()).addOnCompleteListener { showUpdateResult(it.isSuccessful) }
    }

    private fun showUpdateResult(success: Boolean) {
        if (success) {

            finish()
        } else {

        }
    }
}
