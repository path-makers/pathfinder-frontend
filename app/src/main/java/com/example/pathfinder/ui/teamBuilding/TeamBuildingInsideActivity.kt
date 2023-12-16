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
        setupComment()

    }
    private fun getSingleTeamData() {
        viewModel.getSingleTeamData(teamId)

        binding.backButton.setOnClickListener { finish() }
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



    private fun setupComment() {
        commentAdapter = CommentRVAdapter(commentList)
        binding.commentRV.adapter = commentAdapter
        binding.commentRV.layoutManager = LinearLayoutManager(this)
    }


    private fun updateUITeam(teamData: Team) {
        binding.titleArea.text = teamData.title
        binding.textArea.text = teamData.content
        binding.userNameArea.text = teamData.author
        binding.timeArea.text = formatDate(teamData.uploadTime.toLong())
        commentList.clear()
        commentList.addAll(teamData.comment)
        commentAdapter.notifyDataSetChanged()

    }

}
