package com.example.pathfinder.ui.board.view
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardInsideBinding
import com.example.pathfinder.data.models.Board

import com.example.pathfinder.data.models.Comment
import com.example.pathfinder.utils.FBAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardInsideBinding
    private val commentDataList = mutableListOf<Comment>()
    private lateinit var commentAdapter: CommentLVAdapter

    private lateinit var key: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)


        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }
        val boardData = intent.getSerializableExtra("boardData") as? Board

        if (boardData != null) {
            binding.titleArea.text = boardData.title
            binding.textArea.text = boardData.content
            binding.timeArea.text = formatDate(boardData.date.toLong())

            val myUid = FBAuth.getUid()
            val writeUid = boardData.uid
            binding.boardSettingIcon.isVisible = myUid == writeUid
        }


        binding.commentBtn.setOnClickListener {
        //구현필요
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

            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)

        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            //구현필요
            finish()
        }

    }

    private fun formatDate(timeStamp: Long): String {
        val date = Date(timeStamp)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

}
