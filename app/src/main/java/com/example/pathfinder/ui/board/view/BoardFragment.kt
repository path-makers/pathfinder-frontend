package com.example.pathfinder.ui.board.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.R
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.databinding.FragmentBoardBinding
import java.io.Serializable


class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding
    private lateinit var boardRepository: BoardRepository
    private val boardDataList = mutableListOf<Board>()
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

            val intent = Intent(context, BoardInsideActivity::class.java)
            val boardData = boardDataList[position] // boardList는 BoardModel 객체의 리스트입니다.
            intent.putExtra("boardData", boardData as Serializable)
            startActivity(intent)

        }
    }

    private fun initWriteButton() {
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
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


