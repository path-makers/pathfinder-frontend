package com.example.pathfinder.ui.board

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.utils.Common.Companion.formatDate


class BoardRVAdapter(private val boardList: MutableList<Board>) :
    RecyclerView.Adapter<BoardRVAdapter.BoardViewHolder>() {

    // ViewHolder 클래스 정의
    class BoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleArea)
        val content: TextView = view.findViewById(R.id.contentArea)
        val date: TextView = view.findViewById(R.id.timeArea)
        val author: TextView = view.findViewById(R.id.userName)
        val tagsLayout: LinearLayout = view.findViewById(R.id.tagsLayout)
    }

    // 새로운 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_board_list, parent, false)
        return BoardViewHolder(view)
    }

    // 뷰 홀더의 데이터 바인딩
    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board = boardList[position]
        holder.title.text = board.title
        holder.content.text = board.content
        holder.date.text = formatDate(board.date.toLong())
        holder.author.text = board.author
        // 태그 표시 로직
        displayTags(holder.tagsLayout, board.tags)

        holder.itemView.setOnClickListener {
            // 클릭 시 동작
            val intent = Intent(holder.itemView.context, BoardInsideActivity::class.java)
            intent.putExtra("boardId", board.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    // 데이터 셋의 크기 반환
    override fun getItemCount(): Int {
        return boardList.size
    }



    private fun displayTags(tagsLayout: LinearLayout, tags: List<String>) {
        tagsLayout.removeAllViews()
        for (tag in tags) {
            val tagViewLayout = LayoutInflater.from(tagsLayout.context).inflate(R.layout.item_tag, tagsLayout, false)
            val tagTextView = tagViewLayout.findViewById<TextView>(R.id.tagName)
            tagTextView.text = tag
            tagsLayout.addView(tagViewLayout)
        }
    }
}
