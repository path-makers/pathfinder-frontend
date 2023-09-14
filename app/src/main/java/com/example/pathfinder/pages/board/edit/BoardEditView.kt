package com.example.pathfinder.pages.board.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardEditBinding

class BoardEditView : AppCompatActivity() {

    private lateinit var binding: ActivityBoardEditBinding
    private lateinit var controller: BoardEditController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)
        controller = BoardEditController(this)

        val key = intent.getStringExtra("key").toString()

        controller.getBoardData(key) { boardModel ->
            binding.titleArea.setText(boardModel?.title)
            binding.contentArea.setText(boardModel?.content)
        }

        controller.getImageData(key) { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .into(binding.imageArea)
        }

        binding.editBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            controller.editBoardData(key, title, content)
            finish()
        }
    }
}
