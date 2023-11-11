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
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentTeamBuildingBinding
import com.example.pathfinder.data.models.Team
import com.example.pathfinder.ui.board.view.BoardInsideActivity
import com.example.pathfinder.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TeamBuildingFragment : Fragment() {

   private lateinit var binding: FragmentTeamBuildingBinding
    private val teamDataList = mutableListOf<Team>()
    private val teamKeyList = mutableListOf<String>()
    private lateinit var teamRVAdapter: TeamBuildingLVAdapter
    private val TAG = TeamBuildingFragment::class.java.simpleName
    private val db = Firebase.firestore

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

        getFBTeamData()
        initTeamListView()
        initWriteButton()


        return binding.root

    }
    private fun initTeamListView() {
        teamRVAdapter = TeamBuildingLVAdapter(teamDataList)
        binding.teamBuildingListView.adapter = teamRVAdapter
        binding.teamBuildingListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, TeamBuildingInsideActivity::class.java)
            intent.putExtra("key", teamKeyList[position])
            startActivity(intent)
        }
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

