package com.example.pathfinder.ui.board.view

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardWriteBinding
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory
import com.example.pathfinder.ui.components.BottomSheetTagFragment
import com.example.pathfinder.utils.FBAuth

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding
    private val selectedTags = mutableListOf<String>()
    private lateinit var viewModel: BoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        val boardRepository = BoardRepository(this)
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)

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


            viewModel.addBoard(title, content, uid, boardType, selectedTags)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
