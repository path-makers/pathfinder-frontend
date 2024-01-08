package com.example.pathfinder.ui.userProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pathfinder.databinding.ActivityModifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class EditUserProfileActivity : AppCompatActivity() {

    // Firebase 인증 인스턴스 초기화
    private lateinit var auth: FirebaseAuth

    // Data Binding 인스턴스
    private lateinit var binding: ActivityModifyBinding

    // 갤러리에서 이미지를 선택하고 결과를 받는 데 사용하는 ActivityResultLauncher
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    binding.userImage.setImageURI(uri) // 선택된 이미지로 UI 업데이트
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()
        setListeners()
    }

    // UI 컴포넌트를 초기화하는 메서드
    private fun initializeUI() {
        auth = Firebase.auth // Firebase 인증 인스턴스 가져오기

        // 현재 로그인한 사용자 정보로 UI 업데이트
        auth.currentUser?.let { user ->
            Glide.with(this).load(user.photoUrl).circleCrop().into(binding.userImage)
            binding.updateName.setText(user.displayName)
            binding.updateEmail.setText(user.email)
        }
    }

    // 버튼들에 대한 리스너를 설정하는 메서드
    private fun setListeners() {
        binding.buttonBack.setOnClickListener { onBackPressed() } // '뒤로 가기' 버튼
        binding.btnComplete.setOnClickListener { updateProfile() } // '완료' 버튼
        binding.changeImage.setOnClickListener { openGallery() } // '이미지 변경' 버튼
    }

    // 갤러리를 여는 메서드
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(galleryIntent)
    }

    // 사용자 프로필을 업데이트하는 메서드
    private fun updateProfile() {
        val user = auth.currentUser ?: return

        val newUserName = binding.updateName.text.toString()
        val newEmail = binding.updateEmail.text.toString()
        val newPassword = binding.updatePassword.text.toString()

        var updatesCount = 0
        var completedUpdates = 0

        // 사용자 이름 업데이트
        if (newUserName.isNotEmpty()) {
            updatesCount++
            user.updateProfile(userProfileChangeRequest { displayName = newUserName })
                .addOnCompleteListener { checkCompletion(++completedUpdates, updatesCount) }
        }

        // 사용자 이메일 업데이트
        if (newEmail.isNotEmpty()) {
            updatesCount++
            user.updateEmail(newEmail)
                .addOnCompleteListener { checkCompletion(++completedUpdates, updatesCount) }
        }

        // 사용자 비밀번호 업데이트
        if (newPassword.isNotEmpty()) {
            updatesCount++
            user.updatePassword(newPassword)
                .addOnCompleteListener { checkCompletion(++completedUpdates, updatesCount) }
        }

        // 업데이트가 없을 경우 액티비티 종료
        if (updatesCount == 0) finish()
    }

    // 업데이트 완료 여부를 확인하는 메서드
    private fun checkCompletion(completedUpdates: Int, totalUpdates: Int) {
        if (completedUpdates == totalUpdates) {
            setResult(Activity.RESULT_OK)
            finish() // 모든 업데이트가 완료되면 액티비티 종료
        }
    }
}
