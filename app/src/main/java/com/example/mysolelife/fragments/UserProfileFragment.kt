package com.example.mysolelife.fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

import com.example.mysolelife.R
import com.example.mysolelife.databinding.FragmentStoreBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yalantis.ucrop.UCrop
//import com.yalantis.ucrop.UCrop
import java.io.File


class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding
    private val GALLERY_REQUEST_CODE = 1
    private val UCROP_REQUEST_CODE = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)

        val user = Firebase.auth.currentUser

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Update the views
            // For example:
            Glide.with(this)
                .load(photoUrl).circleCrop()
                .into(binding.userImage)
            binding.editUserName.setText(name)
            binding.editEmail.setText(email)
        }


        binding.btnChangeImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)

        }

        binding.btnUpdateName.setOnClickListener {
            val newUserName = binding.editUserName.text.toString()
            val profileUpdates = userProfileChangeRequest {
                displayName = newUserName
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User name updated.")
                        Toast.makeText(context,"닉네임이 변경되었습니다",Toast.LENGTH_LONG).show()
                        binding.editUserName.setText("")
                    }
                }
        }

        binding.btnUpdateEmail.setOnClickListener {
            val newEmail = binding.editEmail.text.toString()

            user!!.updateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User email address updated.")
                        Toast.makeText(context,"이메일이 변경되었습니다",Toast.LENGTH_LONG).show()
                        binding.editEmail.setText("")
                    }
                }
        }

        binding.btnUpdatePassword.setOnClickListener {
            val newPassword = binding.editPassword.text.toString()

            user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User password updated.")
                        Toast.makeText(context,"비밀번호가 변경되었습니다",Toast.LENGTH_LONG).show()
                        binding.editPassword.setText("")
                    }
                }
        }


        binding.homeTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }

        binding.talkTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_talkFragment)
        }


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data?.data != null) {
            val selectedImageUri = data.data

            val destinationUri = Uri.fromFile(File(context?.cacheDir, "cropped"))

            val options = UCrop.Options()
            options.setCircleDimmedLayer(true)

            if (isAdded) {
                UCrop.of(selectedImageUri!!, destinationUri)
                    .withOptions(options)
                    .start(context ?: return, this, UCROP_REQUEST_CODE)
            }


        } else if (requestCode == UCROP_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val resultUri = UCrop.getOutput(data)

            if (resultUri != null) {
                val user = Firebase.auth.currentUser
                val uid = user?.uid

                val storageReference = Firebase.storage.reference.child("userProfiles/$uid")

                storageReference.putFile(resultUri)
                    .addOnSuccessListener {
                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            val profileUpdates = userProfileChangeRequest {
                                photoUri = uri
                            }

                            user!!.updateProfile(profileUpdates)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Glide.with(this)
                                            .load(uri).circleCrop()
                                            .into(binding.userImage)
                                    }
                                }
                        }
                    }
            }
        }
    }
}