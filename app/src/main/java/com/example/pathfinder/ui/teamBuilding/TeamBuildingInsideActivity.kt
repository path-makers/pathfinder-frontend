package com.example.pathfinder.ui.teamBuilding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.databinding.ActivityBoardInsideBinding
import com.example.pathfinder.ui.teamBuilding.viewmodel.TeamBuildingViewModel
import com.example.pathfinder.utils.CommentRVAdapter
import com.example.pathfinder.utils.Common.Companion.formatDate
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamBuildingInsideActivity : AppCompatActivity() {


    private val viewModel: TeamBuildingViewModel by viewModels()
    private lateinit var binding: ActivityBoardInsideBinding
    private val commentList = mutableListOf<Comment>()
    private lateinit var commentAdapter: CommentRVAdapter
    private lateinit var teamId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)
        teamId = intent.getStringExtra("key") ?: return
        getSingleTeamData()
        initializeUI()
    }

    private fun initializeUI() {
        binding.backButton.setOnClickListener { finish() }

        initSingleTeamInside()
//        setupCommentListView()
//        initCommentButton()

    }


    private fun getSingleTeamData() {
        viewModel.getSingleTeamData(teamId)
    }



    private fun initSingleTeamInside() {
        viewModel.singleTeamDataList.observe(this) { result ->
            when (result) {
                is Results.Loading -> {
                    // 로딩 UI 처리
                }

                is Results.Success -> {
                    val teamData = result.data
                    // 성공적으로 데이터를 받았을 때 UI 업데이트
                    updateUITeam(teamData)
                }

                is Results.Failure -> {
                    // 오류 UI 처리
                }
            }
        }
    }

    private fun updateUITeam(teamData: Team) {
        binding.titleArea.text = teamData.title
        binding.textArea.text = teamData.content
        binding.userNameArea.text = teamData.author
        binding.timeArea.text = formatDate(teamData.uploadTime.toLong())
    }

//    private fun setupCommentListView() {
//        commentAdapter = CommentRVAdapter(commentList)
//        binding.commentRV.adapter = commentAdapter
//        binding.commentRV.layoutManager = LinearLayoutManager(this)
//        loadComments(teamId)
//    }

//    private fun initCommentButton() {
//        binding.commentBtn.setOnClickListener {
//            binding.commentArea.text.toString().takeIf { it.isNotBlank() }?.let { commentText ->
//                val comment = Comment(author = getUserName(), content = commentText,createdAt = System.currentTimeMillis())
//                postComment(teamId, comment)
//                binding.commentArea.text.clear()
//            }
//        }
//    }
//
//    private fun getUserName() = Firebase.auth.currentUser?.displayName ?: "Anonymous"
//
//    private fun postComment(teamId: String, comment: Comment) {
//        db.collection("teamBuilding")
//            .document(teamId)
//            .collection("comments")
//            .add(comment)
//            .addOnSuccessListener {
//                // Comment posted successfully
//            }
//            .addOnFailureListener {
//                // Handle the error
//            }
//    }
//
//    private fun loadComments(teamId: String) {
//        db.collection("teamBuilding")
//            .document(teamId)
//            .collection("comments")
//            .orderBy("createdAt")
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    // Handle the error
//
//                    return@addSnapshotListener
//                }
//                snapshot?.toObjects(Comment::class.java)?.let { comments ->
//
//                    commentList.clear()
//                    commentList.addAll(comments)
//                    commentAdapter.notifyDataSetChanged()
//                }
//            }
//    }


}
