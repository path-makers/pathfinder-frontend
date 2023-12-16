package com.example.pathfinder.ui.board

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardWriteBinding
import com.example.pathfinder.ui.board.viewModel.BoardViewModel
import com.example.pathfinder.ui.components.BottomSheetTagFragment
import com.example.pathfinder.utils.FBAuth
import com.hjq.toast.Toaster
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardWriteActivity : AppCompatActivity() {

    private val viewModel: BoardViewModel by viewModels()
    private lateinit var binding: ActivityBoardWriteBinding
    private val selectedTags = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)


        Toaster.init(application)
        initSpinner()
        initBoardWriteButton()
        initTagButton()
    }


    private fun initSpinner() {
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
                displaySelectedTags()
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }



    private fun displaySelectedTags() {
        // 기존에 추가된 태그 뷰를 모두 제거
        binding.tagsLayout.removeAllViews()

        // 선택된 태그를 순회하며 화면에 추가
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
            val errorMessage = "모든 사항을 입력해주세요."

            if (title.isEmpty() || content.isEmpty()) {
                Toaster.show(errorMessage)
            } else {
                viewModel.addBoard(title, content, uid, boardType, selectedTags)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}
