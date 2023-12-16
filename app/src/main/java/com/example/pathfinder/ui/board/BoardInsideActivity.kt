package com.example.pathfinder.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.databinding.ActivityBoardInsideBinding

import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.ui.board.viewModel.BoardViewModel
import com.example.pathfinder.utils.CommentRVAdapter
import com.example.pathfinder.utils.Common.Companion.formatDate
import com.example.pathfinder.utils.FBAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardInsideActivity : AppCompatActivity() {

    private val viewModel: BoardViewModel by viewModels()
    private lateinit var binding: ActivityBoardInsideBinding
    private val commentList = mutableListOf<Comment>()
    private lateinit var commentAdapter: CommentRVAdapter
    private lateinit var boardId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)
        boardId = intent.extras?.get("boardId").toString()


        setupComment()//댓글 리스트뷰 설정
        getBoardDataById(boardId)//보드 아이디로 내부정보 받아옴
        binding.backButton.setOnClickListener { finish() }//뒤로가기 버튼을 누르면 액티비티 종료
        addComment()


    }

    private fun addComment() {
        binding.commentBtn.setOnClickListener {
            viewModel.addComment(
                FBAuth.getUid(),
                binding.commentArea.text.toString(),
                boardId,
                FBAuth.getUserName()
            )

            binding.commentArea.text.clear()
            hideKeyboard()


        }
    }//댓글 작성 버튼을 누르면 댓글을 추가하고 키보드를 숨김
    //todo: 댓글 작성 후 댓글 리스트뷰 업데이트
    //todo: 보드프래그먼트와 비교


    private fun getBoardDataById(boardId: String) {
        viewModel.getBoardDataById(boardId)


        viewModel.boardDataListById.observe(this) { result ->
            when (result) {
                is Results.Loading -> {
                    // 로딩 UI 처리
                }

                is Results.Success -> {
                    val teamData = result.data
                    // 성공적으로 데이터를 받았을 때 UI 업데이트
                    updateUI(teamData)
                }

                is Results.Failure -> {
                    // 오류 UI 처리
                }
            }
        }
    }

    private fun setupComment() {
        commentAdapter = CommentRVAdapter(commentList)
        binding.commentRV.adapter = commentAdapter
        binding.commentRV.layoutManager = LinearLayoutManager(this)
    }

    private fun updateUI(boardData : Board) {
        binding.titleArea.text = boardData.title
        binding.textArea.text = boardData.content
        binding.userNameArea.text = boardData.author
        binding.timeArea.text = formatDate(boardData.createdAt.toLong())
        commentList.clear()
        commentList.addAll(boardData.comments)
        commentAdapter.notifyDataSetChanged()
        displayTags(binding.tagsLayout, boardData.tags)

    }//리사이클러뷰 업데이트






    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.commentArea.windowToken, 0)
    }

    private fun displayTags(tagsLayout: LinearLayout, tags: List<String>) {
        tagsLayout.removeAllViews() // 기존에 추가된 모든 뷰를 제거
        for (tag in tags) {
            // inflate 메서드로 뷰를 생성
            val tagViewLayout = LayoutInflater.from(tagsLayout.context)
                .inflate(R.layout.item_tag, tagsLayout, false)
            // tagName ID를 가진 TextView를 찾음
            val tagTextView = tagViewLayout.findViewById<TextView>(R.id.tagName)
            tagTextView.text = tag // TextView에 태그 텍스트를 설정
            tagsLayout.addView(tagViewLayout) // LinearLayout에 루트 레이아웃을 추가
        }
    }



}
