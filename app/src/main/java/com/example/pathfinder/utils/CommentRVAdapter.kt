package com.example.pathfinder.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Comment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommentRVAdapter(val commentList : MutableList<Comment>) : RecyclerView.Adapter<CommentRVAdapter.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_list,parent,false)
        return CommentViewHolder(view)
    }

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author:TextView = view.findViewById(R.id.userNameArea)
        val content:TextView = view.findViewById(R.id.commentArea)
        val date:TextView = view.findViewById(R.id.timeArea)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.author.text = comment.author
        holder.content.text = comment.content
        holder.date.text = formatDate(comment.createdAt)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }



    private fun formatDate(timeStamp: Long): String {
        val date = Date(timeStamp)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}