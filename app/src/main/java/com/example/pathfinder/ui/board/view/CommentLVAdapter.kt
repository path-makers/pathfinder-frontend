package com.example.pathfinder.ui.board.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Comment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommentLVAdapter(val commentList : MutableList<Comment>):BaseAdapter() {
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if(view==null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_item,parent,false)
        }

        val author = view?.findViewById<TextView>(R.id.userNameArea)
        val title = view?.findViewById<TextView>(R.id.commentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)


        author!!.text = commentList[position].author
        title!!.text = commentList[position].content
        time!!.text = formatDate(commentList[position].createdAt)

        return view!!
    }

    private fun formatDate(timeStamp: Long): String {
        val date = Date(timeStamp)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}