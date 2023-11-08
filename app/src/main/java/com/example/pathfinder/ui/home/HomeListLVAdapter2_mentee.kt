package com.example.pathfinder.ui.home

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

class HomeListLVAdapter2_mentee(val homeList : MutableList<Board>): BaseAdapter() {
    override fun getCount(): Int {
        return homeList.size
    }

    override fun getItem(position: Int): Any {
        return homeList[position]
    }


    override fun getItemId(position:  Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        view = LayoutInflater.from(parent?.context).inflate(R.layout.home_list_item2, parent, false)


        val tagsLayout = view.findViewById<LinearLayout>(R.id.tagsLayout)
        val title = view?.findViewById<TextView>(R.id.titleArea)

        if (tagsLayout != null && homeList[position].tags.isNotEmpty()) {
            displayTags(tagsLayout, homeList[position].tags)
        }
        title!!.text = homeList[position].title

        return view!!
    }


    private fun displayTags(tagsLayout: LinearLayout, tags: List<String>) {
        tagsLayout.removeAllViews() // 기존에 추가된 모든 뷰를 제거

        // inflate 메서드로 뷰를 생성
        val tagViewLayout = LayoutInflater.from(tagsLayout.context).inflate(R.layout.item_tag, tagsLayout, false)
        // tagName ID를 가진 TextView를 찾음
        val tagTextView = tagViewLayout.findViewById<TextView>(R.id.tagName)
        tagTextView.text = tags[0] // TextView에 태그 텍스트를 설정
        tagsLayout.addView(tagViewLayout) // LinearLayout에 루트 레이아웃을 추가
    }
}