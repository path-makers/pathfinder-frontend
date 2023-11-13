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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentTeamBuildingBinding
import com.example.pathfinder.data.models.Team
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TeamBuildingFragment : Fragment() {

   private lateinit var binding: FragmentTeamBuildingBinding
    private val teamDataList = mutableListOf<Team>()
    private val teamKeyList = mutableListOf<String>()
    private lateinit var teamRVAdapter: TeamBuildingRVAdapter
    private val TAG = TeamBuildingFragment::class.java.simpleName
    private val db = Firebase.firestore
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val startWriteActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            getFBTeamData()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_building, container, false)


        initTeamListView()
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



    private fun initTeamListView() {
        teamRVAdapter = TeamBuildingRVAdapter(teamDataList, teamKeyList)
        binding.teamBuildingRecyclerView.adapter = teamRVAdapter
        binding.teamBuildingRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initWriteButton() {
        binding.teamWriteBtn.setOnClickListener {
            val intent = Intent(context, TeamBuildingWriteActivity::class.java)
            startWriteActivityForResult.launch(intent)
        }
    }

    private fun getFBTeamData() {
        db.collection("teamBuilding")
            .orderBy("uploadTime", Query.Direction.ASCENDING)
            .addSnapshotListener { teamdatas, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (teamdatas != null) {
                    Log.d(TAG, "Current data: ${teamdatas.documents}")
                    handleSnapshot(teamdatas)
                }
            }
    }

    private fun handleSnapshot(teamdatas: QuerySnapshot) {
        teamDataList.clear()
        teamKeyList.clear()

        for (teamdata in teamdatas) {
            Log.d(TAG, teamdata.id + " => " + teamdata.data)
            val item = teamdata.toObject(Team::class.java)
            teamDataList.add(item)
            teamKeyList.add(teamdata.id)
        }

        teamDataList.reverse()
        teamKeyList.reverse()
        teamRVAdapter.notifyDataSetChanged()
    }

}

