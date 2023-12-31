package com.example.pathfinder.ui.userProfile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentUserProfileBinding
import com.example.pathfinder.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.yalantis.ucrop.UCrop


class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val GALLERY_REQUEST_CODE = 1
    private val UCROP_REQUEST_CODE = 2

    private var profileAdapter: ProfileAdapter? = null
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private var tabCurrentIdx = 0

    companion object {
        private const val MODIFY_REQUEST_CODE = 1001
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
            startActivityForResult(intent, MODIFY_REQUEST_CODE) // MODIFY_REQUEST_CODE는 정수 상수
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





        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MODIFY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateUserProfileUI() // UI 업데이트
        }
    }

    private fun updateUserProfileUI() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Firebase에서 최신 사용자 정보 불러오기
            binding.userName.setText(it.displayName)
            binding.email.setText(it.email)
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