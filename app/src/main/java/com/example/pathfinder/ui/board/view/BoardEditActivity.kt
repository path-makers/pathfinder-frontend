package com.example.pathfinder.ui.board.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardEditBinding

class BoardEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardEditBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)


        val key = intent.getStringExtra("key").toString()
        //구현필요

        initEditBtn()
    }

    private fun initEditBtn() {
        binding.editBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            //구현필요
            finish()
        }
    }


}

