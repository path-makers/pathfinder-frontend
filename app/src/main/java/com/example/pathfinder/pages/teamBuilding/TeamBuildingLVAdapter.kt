package com.example.pathfinder.pages.teamBuilding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R

class TeamBuildingLVAdapter(val teamBuildingList : MutableList<TeamModel>): BaseAdapter(){
    override fun getCount(): Int {
        return teamBuildingList.size
    }

    override fun getItem(position: Int): Any {
        return teamBuildingList[position]
    }


    override fun getItemId(position:  Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent : ViewGroup?): View {

//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_building, container, false)

      

        var view = convertView

        //if(view==null){
        view = LayoutInflater.from(parent?.context).inflate(R.layout.team_building_list_item,parent,false)
        //}

        val itemLinearLayoutView = view?.findViewById<ConstraintLayout>(R.id.teamItemView)

        val teamCategory = view?.findViewById<TextView>(R.id.tvCategory)
        val teamTitle = view?.findViewById<TextView>(R.id.tvTitle)
        val teamContent = view?.findViewById<TextView>(R.id.tvContent)
        val teamRecruitTime = view?.findViewById<TextView>(R.id.tvPeriod)
        val teamRegion = view?.findViewById<TextView>(R.id.tvRegion)
        val teamDisplayName = view?.findViewById<TextView>(R.id.tvUserName)
        val teamUploadTime = view?.findViewById<TextView>(R.id.tvPostTime)
        val teamLikeCount = view?.findViewById<TextView>(R.id.tvHeartCount)
        val teamCommentCount = view?.findViewById<TextView>(R.id.tvMessageCount)

        //아래 카운트 2개 확인하기


        teamCategory!!.text = teamBuildingList[position].category
        teamTitle!!.text = teamBuildingList[position].title
        teamContent!!.text = teamBuildingList[position].content
        teamRecruitTime!!.text = teamBuildingList[position].recruitTime
        teamRegion!!.text = teamBuildingList[position].region
        teamDisplayName!!.text = teamBuildingList[position].displayName
        teamUploadTime!!.text = teamBuildingList[position].uploadTime
        teamLikeCount!!.text = teamBuildingList[position].likeCount.toString()
        teamCommentCount!!.text = teamBuildingList[position].commentCount.toString()


        return view!!
    }

}