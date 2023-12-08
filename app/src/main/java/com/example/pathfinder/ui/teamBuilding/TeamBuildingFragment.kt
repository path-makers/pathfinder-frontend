package com.example.pathfinder.ui.teamBuilding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.databinding.FragmentTeamBuildingBinding
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.ui.teamBuilding.viewmodel.TeamBuildingViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamBuildingFragment : Fragment() {

    private val viewModel: TeamBuildingViewModel by viewModels()
    private lateinit var binding: FragmentTeamBuildingBinding
    private lateinit var teamRVAdapter: TeamBuildingRVAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val startWriteActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                getFBTeamData()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_building, container, false)
        // 메뉴


        initTeamRecyclerView()
        initWriteButton()


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding.swipeRefreshLayout


        getFBTeamData()

        swipeRefreshLayout.setOnRefreshListener {
            // 사용자가 새로고침을 요청하면 실행될 로직
            getFBTeamData()

            // 데이터 로딩이 끝난 후에는 새로고침 종료
            swipeRefreshLayout.isRefreshing = false
        }//pull to refresh 구현


    }//뷰가 생성된 후 데이터를 연결






        private fun initTeamRecyclerView() {
            viewModel.teamDataList.observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Results.Success -> {
                        teamRVAdapter = TeamBuildingRVAdapter(result.data as MutableList<Team>)
                        binding.teamBuildingRecyclerView.adapter = teamRVAdapter
                    }
                    is Results.Loading -> {
                        // 로딩 처리
                    }
                    is Results.Failure -> {
                        // 오류 처리
                    }
                }
            })
            binding.teamBuildingRecyclerView.layoutManager = LinearLayoutManager(context)
        }


    private fun initWriteButton() {
        binding.teamWriteBtn.setOnClickListener {
            val intent = Intent(context, TeamBuildingWriteActivity::class.java)
            startWriteActivityForResult.launch(intent)
        }
    }

    private fun getFBTeamData() {
     viewModel.getTeamData()
    }





}

