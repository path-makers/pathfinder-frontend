package com.example.pathfinder.pages.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.pathfinder.R
import com.example.pathfinder.utils.FBAuth

class BoardListLVAdapter(val boardList : MutableList<BoardModel>): BaseAdapter(){
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

        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val date = view?.findViewById<TextView>(R.id.timeArea)  // 이름을 time에서 date로 변경
        val uid = view?.findViewById<TextView>(R.id.userName)    // 이름을 userName에서 uid로 변경


        title!!.text = boardList[position].title
        content!!.text = boardList[position].content
        date!!.text = boardList[position].date
        uid!!.text = boardList[position].uid

        return view!!
    }

}