package com.example.pathfinder.ui.userProfile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentUserProfileBinding
import com.example.pathfinder.ui.board.view.BoardWriteActivity
import com.example.pathfinder.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yalantis.ucrop.UCrop
//import com.yalantis.ucrop.UCrop
import java.io.File


class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val GALLERY_REQUEST_CODE = 1
    private val UCROP_REQUEST_CODE = 2

    private var profileAdapter: ProfileAdapter? = null
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private var tabCurrentIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

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
            binding.userName.setText(name)
            binding.email.setText(email)
        }

        binding.button2.setOnClickListener{
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val intent = Intent(context, ModifyActivity::class.java)
            startActivity(intent)
        }

        // 탭레이아웃 + 뷰페이저
        tabLayout = binding.profileTabLayout
        viewPager = binding.profileViewPager

        binding.profileTabLayout.addTab(binding.profileTabLayout.newTab().setCustomView(createCustomTabView("작성글")))
        binding.profileTabLayout.addTab(binding.profileTabLayout.newTab().setCustomView(createCustomTabView("댓글")))
        binding.profileTabLayout.addTab(binding.profileTabLayout.newTab().setCustomView(createCustomTabView("북마크")))

        // 커스텀 어댑터 생성
        profileAdapter = ProfileAdapter(childFragmentManager, tabLayout.tabCount)
        viewPager.adapter = profileAdapter
        viewPager.currentItem = tabCurrentIdx
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                tabCurrentIdx = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

//        binding.btnChangeImage.setOnClickListener {
//            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//
//            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
//
//        }
//
//        binding.btnUpdateName.setOnClickListener {
//            val newUserName = binding.editUserName.text.toString()
//            val profileUpdates = userProfileChangeRequest {
//                displayName = newUserName
//            }
//
//            user!!.updateProfile(profileUpdates)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d(TAG, "User name updated.")
//                        Toast.makeText(context,"닉네임이 변경되었습니다",Toast.LENGTH_LONG).show()
//                        binding.editUserName.setText("")
//                    }
//                }
//        }
//
//        binding.btnUpdateEmail.setOnClickListener {
//            val newEmail = binding.editEmail.text.toString()
//
//            user!!.updateEmail(newEmail)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d(TAG, "User email address updated.")
//                        Toast.makeText(context,"이메일이 변경되었습니다",Toast.LENGTH_LONG).show()
//                        binding.editEmail.setText("")
//                    }
//                }
//        }
//
//        binding.btnUpdatePassword.setOnClickListener {
//            val newPassword = binding.editPassword.text.toString()
//
//            user!!.updatePassword(newPassword)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d(TAG, "User password updated.")
//                        Toast.makeText(context,"비밀번호가 변경되었습니다",Toast.LENGTH_LONG).show()
//                        binding.editPassword.setText("")
//                    }
//                }
//        }



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

    private fun createCustomTabView(tabNm: String): View {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tabView = inflater.inflate(R.layout.custom_tab, null)
        val tv_tab_name = tabView.findViewById<TextView>(R.id.tv_tab_nm)
        tv_tab_name.text = tabNm
        return tabView
    }
}