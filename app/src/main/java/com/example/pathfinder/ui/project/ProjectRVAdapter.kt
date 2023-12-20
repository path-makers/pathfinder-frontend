package com.example.pathfinder.ui.project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Project

class ProjectRVAdapter(private val projectBuildingList: MutableList<Project>) :
    RecyclerView.Adapter<ProjectRVAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.tvUserName)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val content: TextView = view.findViewById(R.id.tvContent)
        val category: TextView = view.findViewById(R.id.tvCategory)
        val region: TextView = view.findViewById(R.id.tvRegion)
        val endTime: TextView = view.findViewById(R.id.tvPeriod)
        val createdAt: TextView = view.findViewById(R.id.tvPostTime)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_project_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = projectBuildingList[position]
        holder.author.text = item.author
        holder.title.text = item.title
        holder.content.text = item.content
        holder.category.text = item.category
        holder.region.text = item.region
        holder.endTime.text = item.endTime
        holder.createdAt.text = item.uploadTime



        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ProjectInsideActivity::class.java)
            intent.putExtra("key", item.id)
            holder.itemView.context.startActivity(intent)
            //todo: 한번 더 확인
        }




    }

    override fun getItemCount() = projectBuildingList.size
}
