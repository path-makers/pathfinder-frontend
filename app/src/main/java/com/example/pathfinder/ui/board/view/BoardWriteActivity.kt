package com.example.pathfinder.ui.board.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardWriteBinding
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.ui.components.BottomSheetTagFragment
import com.example.pathfinder.utils.FBAuth

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding
    private val repository = BoardRepository(this)
    private val selectedTags = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        initBoardWriteButton()
        initTagButton()
    }


    private fun initTagButton() {
        binding.addButton.setOnClickListener {
            val bottomSheet = BottomSheetTagFragment { tags ->
                selectedTags.clear()
                selectedTags.addAll(tags)
                // TODO: 태그들 화면에 보이게 추가해야함
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
    private fun initBoardWriteButton() {
        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val boardType = "MENTEE"


            val board = Board(title, content, uid, "", boardType, selectedTags)
            repository.sendBoardData(board)

            finish()
        }
    }
}
