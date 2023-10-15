package com.example.pathfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pathfinder.R
import com.example.pathfinder.data.BoardRepository
import com.example.pathfinder.pages.board.BoardListLVAdapter
import com.example.pathfinder.pages.board.Inside.BoardInsideView
import com.example.pathfinder.pages.board.BoardModel
import com.example.pathfinder.databinding.FragmentBoardBinding
import com.example.pathfinder.pages.board.write.BoardWriteView
import com.example.pathfinder.pages.teamBuilding.TeamBuildingLVAdapter
import com.example.pathfinder.pages.teamBuilding.TeamBuildingWriteActivity
import com.example.pathfinder.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable


class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding
    private lateinit var boardRepository: BoardRepository
    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var boardRVAdapter: BoardListLVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        boardRepository = BoardRepository(requireContext())


        initBoardListView()
        initWriteButton()
        getFBBoardData()

        return binding.root
    }

    private fun initBoardListView() {
        boardRVAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter

        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(context, BoardInsideView::class.java)
            val boardData = boardDataList[position] // boardList는 BoardModel 객체의 리스트입니다.
            intent.putExtra("boardData", boardData as Serializable)
            startActivity(intent)

        }
    }

    private fun initWriteButton() {
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteView::class.java)
            startActivity(intent)
        }
    }


    private fun getFBBoardData() {
        boardRepository.getFBBoardData({ boardDataList ->
            this.boardDataList.addAll(boardDataList)
            this.boardDataList.reverse()
            boardRVAdapter.notifyDataSetChanged()
        }, { errorMessage ->
            // 에러 처리
        })
    }

}


