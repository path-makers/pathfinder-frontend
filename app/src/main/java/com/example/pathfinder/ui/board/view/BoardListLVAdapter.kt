package com.example.pathfinder.ui.board.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Board
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BoardListLVAdapter(val boardList : MutableList<Board>): BaseAdapter(){
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }


    override fun getItemId(position:  Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false)


        val tagsLayout = view.findViewById<LinearLayout>(R.id.tagsLayout)
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val date = view?.findViewById<TextView>(R.id.timeArea)  // 이름을 time에서 date로 변경
        val author = view?.findViewById<TextView>(R.id.userName)    // 이름을 userName에서 uid로 변경

        if (tagsLayout != null && boardList[position].tags.isNotEmpty()) {
            displayTags(tagsLayout, boardList[position].tags)
        }

        title!!.text = boardList[position].title
        content!!.text = boardList[position].content
        date!!.text = formatDate(boardList[position].date.toLong())
        author!!.text = boardList[position].author

        return view!!
    }

    private fun formatDate(timeStamp: Long): String {
        val date = Date(timeStamp)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

    private fun displayTags(tagsLayout: LinearLayout, tags: List<String>) {
        tagsLayout.removeAllViews() // 기존에 추가된 모든 뷰를 제거
        for (tag in tags) {
            // inflate 메서드로 뷰를 생성
            val tagViewLayout = LayoutInflater.from(tagsLayout.context).inflate(R.layout.item_tag, tagsLayout, false)
            // tagName ID를 가진 TextView를 찾음
            val tagTextView = tagViewLayout.findViewById<TextView>(R.id.tagName)
            tagTextView.text = tag // TextView에 태그 텍스트를 설정
            tagsLayout.addView(tagViewLayout) // LinearLayout에 루트 레이아웃을 추가
        }
    }
}