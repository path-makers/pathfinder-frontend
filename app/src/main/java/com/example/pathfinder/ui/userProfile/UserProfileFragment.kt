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
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentUserProfileBinding
import com.example.pathfinder.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    // 변수 선언 부분
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var binding: FragmentUserProfileBinding  // 뷰 바인딩
    private val GALLERY_REQUEST_CODE = 1
    private val UCROP_REQUEST_CODE = 2
    private var profileAdapter: ProfileAdapter? = null  // 프로필 어댑터
    private lateinit var tabLayout: TabLayout  // 탭 레이아웃
    private lateinit var viewPager: ViewPager  // 뷰페이저
    private var tabCurrentIdx = 0  // 현재 탭 인덱스
    val user = Firebase.auth.currentUser  // 현재 로그인한 사용자 정보

    companion object {
        private const val MODIFY_REQUEST_CODE = 1001  // 수정 요청 코드
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        // Firebase에서 사용자 정보를 불러오는 부분

        user?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // 뷰 업데이트
            Glide.with(this).load(photoUrl).circleCrop().into(binding.userImage)
            binding.userName.setText(name)
            binding.email.setText(email)
        }

        // 설정 화면으로 이동하는 버튼 이벤트
        binding.button2.setOnClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }

        // 수정 화면으로 이동하는 버튼 이벤트
        binding.button.setOnClickListener {
            val intent = Intent(context, ModifyActivity::class.java)
            startActivityForResult(intent, MODIFY_REQUEST_CODE)
        }

        // 탭 레이아웃 및 뷰페이저 설정 부분
        setupTabLayoutAndViewPager()

        return binding.root
    }

    private fun setupTabLayoutAndViewPager() {
        tabLayout = binding.profileTabLayout
        viewPager = binding.profileViewPager

        // 탭 추가
        binding.profileTabLayout.addTab(binding.profileTabLayout.newTab().setCustomView(createCustomTabView("작성글")))
        binding.profileTabLayout.addTab(binding.profileTabLayout.newTab().setCustomView(createCustomTabView("댓글")))
        binding.profileTabLayout.addTab(binding.profileTabLayout.newTab().setCustomView(createCustomTabView("북마크")))

        // 어댑터 설정 및 페이지 리스너 연결
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
    }

    // 결과 받기 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MODIFY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateUserProfileUI() // 사용자 프로필 UI 업데이트
        }
    }

    // 사용자 프로필 UI 업데이트 메소드
    private fun updateUserProfileUI() {
        user?.let {
            binding.userName.setText(it.displayName)
            binding.email.setText(it.email)
        }
    }

    // 커스텀 탭 뷰 생성 메소드
    private fun createCustomTabView(tabNm: String): View {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tabView = inflater.inflate(R.layout.custom_tab, null)
        val tv_tab_name = tabView.findViewById<TextView>(R.id.tv_tab_nm)
        tv_tab_name.text = tabNm
        return tabView
    }
}
