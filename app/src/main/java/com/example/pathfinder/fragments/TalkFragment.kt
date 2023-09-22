package com.example.pathfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.pages.board.BoardListLVAdapter
import com.example.pathfinder.pages.board.Inside.BoardInsideView
import com.example.pathfinder.pages.board.BoardModel
import com.example.pathfinder.databinding.FragmentTalkBinding
import com.example.pathfinder.pages.board.write.BoardWriteView
import com.example.pathfinder.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class TalkFragment : Fragment() {
    private lateinit var binding: FragmentTalkBinding

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = TalkFragment::class.java.simpleName

    private lateinit var boardRVAdapter: BoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)




        boardRVAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter



        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            //첫번쨰 방법으로는 listview에 있는 데이터 title content time 다 다른 액티비티로
            //전달해줘서 만들기
//            val intent = Intent(context,BoardInsideActivity::class.java)
//            intent.putExtra("title",boardDataList[position].title)
//            intent.putExtra("content",boardDataList[position].content)
//            intent.putExtra("time",boardDataList[position].time)
//            startActivity(intent)

            //두번째 방법으로는 firebase에 있는 board에 대한 데이터의 id를 기반으로 다시 데이터를
            //받아오는 방법
            val intent = Intent(context, BoardInsideView::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)

        }


        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteView::class.java)
            startActivity(intent)
        }


        getFBBoardData()

        return binding.root
    }

    private fun getFBBoardData() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel in snapshot.children) {
                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }

                boardKeyList.reverse()
                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()
                Log.d(TAG, boardDataList.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", error.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)

    }
}