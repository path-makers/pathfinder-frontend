package com.example.pathfinder.pages.board.write

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardWriteBinding
import com.example.pathfinder.pages.board.BoardModel

import com.example.pathfinder.utils.FBAuth
import com.example.pathfinder.utils.FBRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BoardWriteView : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding
    private lateinit var controller: BoardWriteController
    private var isImageUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)
        controller = BoardWriteController()

        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()
            val userEmail = FBAuth.getEmail()
            val displayName = Firebase.auth.currentUser?.displayName

            val board = BoardModel(title, content, uid, time)
            val key = FBRef.boardRef.push().key.toString()

            controller.saveBoardData(board, key)

            if (isImageUpload) {
                controller.uploadImageData(key, binding.imageArea)
            }
            finish()
        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }
}
