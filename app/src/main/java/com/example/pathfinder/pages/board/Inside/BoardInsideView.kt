package com.example.pathfinder.pages.board.Inside
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardInsideBinding
import com.example.pathfinder.pages.board.BoardModel
import com.example.pathfinder.pages.board.edit.BoardEditView

import com.example.pathfinder.pages.board.CommentLVAdapter
import com.example.pathfinder.pages.board.CommentModel
import com.example.pathfinder.utils.FBAuth


import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BoardInsideView : AppCompatActivity() {

    private lateinit var binding: ActivityBoardInsideBinding
    private val commentDataList = mutableListOf<CommentModel>()
    private lateinit var commentAdapter: CommentLVAdapter

    private lateinit var key: String
    private lateinit var controller: BoardInsideController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)
        controller = BoardInsideController(this)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }
        val boardData = intent.getSerializableExtra("boardData") as? BoardModel

        if (boardData != null) {
            binding.titleArea.text = boardData.title
            binding.textArea.text = boardData.content
            binding.timeArea.text = boardData.date

            val myUid = FBAuth.getUid()
            val writeUid = boardData.uid
            binding.boardSettingIcon.isVisible = myUid == writeUid
        }







        binding.commentBtn.setOnClickListener {
            val displayName = Firebase.auth.currentUser?.displayName ?: "Anonymous"
            val comment = CommentModel(
                binding.commentArea.text.toString(),
                FBAuth.getTime(),
                displayName
            )
            controller.insertComment(key, comment)
            binding.commentArea.setText("")
        }

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

    }

    private fun showDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {

            val intent = Intent(this, BoardEditView::class.java)
            intent.putExtra("key", key)
            startActivity(intent)

        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            controller.removeBoard(key)
            finish()
        }

    }

}
