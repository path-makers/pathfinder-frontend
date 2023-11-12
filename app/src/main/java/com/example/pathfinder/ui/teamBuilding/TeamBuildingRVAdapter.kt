package com.example.pathfinder.ui.teamBuilding

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Team
import com.example.pathfinder.ui.board.view.BoardInsideActivity

class TeamBuildingRVAdapter(private val teamBuildingList: MutableList<Team>,private val teamBuildingKeyList: MutableList<String>) :
    RecyclerView.Adapter<TeamBuildingRVAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamCategory: TextView = view.findViewById(R.id.tvCategory)
        val teamTitle: TextView = view.findViewById(R.id.tvTitle)
        val teamContent: TextView = view.findViewById(R.id.tvContent)
        val teamRecruitTime: TextView = view.findViewById(R.id.tvPeriod)
        val teamRegion: TextView = view.findViewById(R.id.tvRegion)
        val teamDisplayName: TextView = view.findViewById(R.id.tvUserName)
        val teamUploadTime: TextView = view.findViewById(R.id.tvPostTime)
        val teamLikeCount: TextView = view.findViewById(R.id.tvHeartCount)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team_building_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = teamBuildingList[position]
        holder.teamCategory.text = item.category
        holder.teamTitle.text = item.title
        holder.teamContent.text = item.content
        holder.teamRecruitTime.text = item.recruitTime
        holder.teamRegion.text = item.regionArea
        holder.teamDisplayName.text = item.displayName
        holder.teamUploadTime.text = item.uploadTime
        holder.teamLikeCount.text = item.likeCount.toString()


        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, TeamBuildingInsideActivity::class.java)
            intent.putExtra("key", teamBuildingKeyList[position])
            it.context.startActivity(intent)
        }


    }

    override fun getItemCount() = teamBuildingList.size
}
