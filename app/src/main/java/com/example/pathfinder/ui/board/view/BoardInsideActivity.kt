package com.example.pathfinder.ui.board.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pathfinder.R
import com.example.pathfinder.databinding.ActivityBoardInsideBinding
import com.example.pathfinder.data.models.Board

import com.example.pathfinder.data.models.Comment
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory
import com.example.pathfinder.utils.FBAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardInsideBinding
    private val commentDataList = mutableListOf<Comment>()
    private lateinit var commentAdapter: CommentLVAdapter
    private lateinit var viewModel: BoardViewModel
    private lateinit var boardId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)
        boardId = intent.extras?.get("boardId").toString()

        setupViewModel()//뷰모델 설정
        setupCommentListView()//댓글 리스트뷰 설정
        getFBBoardDataById(boardId)//보드 아이디로 내부정보 받아옴


        binding.commentBtn.setOnClickListener {
            viewModel.addComment(FBAuth.getUid(), binding.commentArea.text.toString(), boardId)
            binding.commentArea.text.clear()
            hideKeyboard()

        }//댓글 작성 버튼을 누르면 댓글을 추가하고 키보드를 숨김

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.boardSettingIcon.setOnClickListener {
//            showDialog()
        }

    }



    private fun getFBBoardDataById(boardId: String) {
        viewModel.getBoardDataById(boardId)
        viewModel.singleBoardData.observe(this) { board ->

            board?.let {
            binding.typeArea.text = board.boardType
            binding.titleArea.text = board.title
            binding.tagsLayout.isVisible = board.tags.isNotEmpty()
            binding.textArea.text = board.content
            binding.timeArea.text = formatDate(board.date.toLong())
            viewModel.commentsData.value = board.comments



            displayTags(binding.tagsLayout, board.tags)
            val myUid = FBAuth.getUid()
            val writeUid = board.uid

//            commentDataList.addAll(board.comments)
            binding.boardSettingIcon.isVisible = myUid == writeUid
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->

        }
    }


    private fun setupViewModel() {
        val boardRepository = BoardRepository(this)
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)


        viewModel.commentsData.observe(this, Observer { comments ->
            commentAdapter.commentList.clear()
            commentAdapter.commentList.addAll(comments)
            commentAdapter.notifyDataSetChanged()
        })


    }
    private fun setupCommentListView() {
        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter
    }

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

    private fun showDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {

            val intent = Intent(this, BoardEditActivity::class.java)

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
