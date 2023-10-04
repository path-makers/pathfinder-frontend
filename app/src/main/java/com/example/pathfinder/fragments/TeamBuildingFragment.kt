package com.example.pathfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentTeamBuildingBinding
import com.example.pathfinder.pages.teamBuilding.TeamModel
import com.example.pathfinder.pages.board.Inside.BoardInsideView
import com.example.pathfinder.pages.teamBuilding.TeamBuildingLVAdapter
import com.example.pathfinder.pages.board.write.BoardWriteView
import com.example.pathfinder.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TeamBuildingFragment : Fragment() {

   private lateinit var binding: FragmentTeamBuildingBinding
    private val teamDataList = mutableListOf<TeamModel>()
    private val teamKeyList = mutableListOf<String>()
    private lateinit var teamRVAdapter: TeamBuildingLVAdapter
    private val TAG = TeamBuildingFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_building, container, false)
        teamRVAdapter = TeamBuildingLVAdapter(teamDataList)
        binding.teamBuildingListView.adapter = teamRVAdapter

        binding.teamBuildingListView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(context, BoardInsideView::class.java)
            //추가 확인하기
            intent.putExtra("key", teamKeyList[position])
            startActivity(intent)

        }
        //팀 빌딩 게시글 안으로 들어가기

        binding.teamWriteBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteView::class.java)
            //확인하기
            startActivity(intent)
        }


        getFBTeamData()

        return binding.root



    }

    private fun getFBTeamData() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                teamDataList.clear()

                for (dataModel in snapshot.children) {
                    Log.d(TAG, dataModel.toString())
                    val item = dataModel.getValue(TeamModel::class.java)
                    teamDataList.add(item!!)
                    teamKeyList.add(dataModel.key.toString())
                }

                teamKeyList.reverse()
                teamDataList.reverse()
                teamRVAdapter.notifyDataSetChanged()
                Log.d(TAG, teamDataList.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", error.toException())
            }
        }
        FBRef.teamBuildingRef.addValueEventListener(postListener)

    }
    //다시 확인하기
}

// 어째서 private아닌데 사용가능했는지 확인하기