package com.example.pathfinder.ui.board.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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

        initSpinner()
        initBoardWriteButton()
        initTagButton()
    }


    private fun initSpinner() {
        //todo: 스피너 어댑터 설정 추가 공부
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.boardType.adapter = adapter
        }

    }


    private fun initTagButton() {
        binding.addButton.setOnClickListener {
            val bottomSheet = BottomSheetTagFragment { tags ->
                selectedTags.clear()
                selectedTags.addAll(tags)
                // TODO: 태그들 화면에 보이게 추가해야함
                displaySelectedTags()
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }



    private fun displaySelectedTags() {
        // 기존에 추가된 태그 뷰를 모두 제거합니다.
        binding.tagsLayout.removeAllViews()

        // 선택된 태그를 순회하며 화면에 추가합니다.
        for (tag in selectedTags) {
            val tagView = LayoutInflater.from(this).inflate(R.layout.item_tag, binding.tagsLayout, false)
            val tagName = tagView.findViewById<TextView>(R.id.tagName)
            tagName.text = tag
            binding.tagsLayout.addView(tagView)
        }
    }


    private fun initBoardWriteButton() {
        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val boardType = when (binding.boardType.selectedItem.toString()) {
                "멘토" -> "MENTOR"
                else -> "MENTEE"}


            viewModel.addBoard(title, content, uid, boardType, selectedTags)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
