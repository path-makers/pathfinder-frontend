package com.example.pathfinder.ui.userProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentUserProfileBinding
import com.example.pathfinder.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var binding: FragmentUserProfileBinding
    private val user = Firebase.auth.currentUser

    private val editUserProfileActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                updateUserProfileUI()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        setupUserProfile()
        setupTabLayoutAndViewPager()
        return binding.root
    }

    private fun setupUserProfile() {
        // Firebase에서 사용자 정보를 불러오는 부분
        user?.let {
            Glide.with(this).load(it.photoUrl).circleCrop().into(binding.userImage)
            binding.userName.text = it.displayName
            binding.email.text = it.email
        }

        // 설정 화면으로 이동하는 버튼 이벤트
        binding.button2.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }

        // 수정 화면으로 이동하는 버튼 이벤트
        binding.button.setOnClickListener {
            val intent = Intent(context, EditUserProfileActivity::class.java)
            editUserProfileActivityForResult.launch(intent)
        }
    }

    private fun updateUserProfileUI() {
        user?.let {
            binding.userName.text = it.displayName
            binding.email.text = it.email
        }
    }

    private fun setupTabLayoutAndViewPager() {
        val tabTitles = arrayOf("작성글", "댓글", "북마크")
        val fragments = arrayOf(UserWrittenPostsFragment(), UserCommentsFragment(), UserBookmarksFragment())

        val adapter = UserProfileTabsAdapter(this, fragments)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}

class UserProfileTabsAdapter(
    fragment: Fragment,
    private val fragments: Array<Fragment>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}