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

        initBoardWriteButton()


    }

    private fun initBoardWriteButton() {
        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()

            val boardType = "MENTEE"
            val tags = listOf("IT", "TEST") //  우선 2개는 임시로 넣어놓음


            val board = BoardModel(title, content, uid, "", boardType, tags)

            controller.sendBoardData(board, this)


            finish()

        }
    }


}
