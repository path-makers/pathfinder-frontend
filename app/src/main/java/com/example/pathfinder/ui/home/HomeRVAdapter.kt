package com.example.pathfinder.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.ui.board.view.BoardInsideActivity
import java.lang.StrictMath.min

class HomeRVAdapter(private val boardList : MutableList<Board>):
    RecyclerView.Adapter<HomeRVAdapter.HomeViewHolder>() {


    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleArea)
        val tagsLayout: LinearLayout = view.findViewById(R.id.tagsLayout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false)
        return HomeViewHolder(view)
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val board = boardList[position]
        holder.title.text = board.title
        if (board.tags.isNotEmpty()) {
            displayTags(holder.tagsLayout, board.tags)
            holder.tagsLayout.visibility = View.VISIBLE
        }
        else {
            holder.tagsLayout.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            // 클릭 시 동작
            val intent = Intent(holder.itemView.context, BoardInsideActivity::class.java)
            intent.putExtra("boardId", board.id)
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return min(boardList.size, 5)
    }



    private fun displayTags(tagsLayout: LinearLayout, tags: List<String>) {
        tagsLayout.removeAllViews()
        // 첫 번째 태그만 표시
        if (tags.isNotEmpty()) {
            val tagViewLayout = LayoutInflater.from(tagsLayout.context).inflate(R.layout.item_tag, tagsLayout, false)
            val tagTextView = tagViewLayout.findViewById<TextView>(R.id.tagName)
            tagTextView.text = tags[0]  // 첫 번째 태그
            tagsLayout.addView(tagViewLayout)
        }
    }

}