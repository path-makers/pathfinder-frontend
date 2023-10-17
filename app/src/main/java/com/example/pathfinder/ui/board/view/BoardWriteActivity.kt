package com.example.pathfinder.ui.board.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardWriteBinding
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.utils.FBAuth

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding
    private val repository = BoardRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        initBoardWriteButton()
    }

    private fun initBoardWriteButton() {
        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val boardType = "MENTEE"
            val tags = listOf("IT", "TEST")

            val board = Board(title, content, uid, "", boardType, tags)
            repository.sendBoardData(board)

            finish()
        }
    }
}
