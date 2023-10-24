package com.example.pathfinder.ui.board.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pathfinder.R
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.databinding.FragmentBoardBinding
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel
import java.io.Serializable


class BoardFragment : Fragment() {
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding: FragmentBoardBinding

    private val boardDataList = mutableListOf<Board>()
    private lateinit var boardRVAdapter: BoardListLVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)

        val boardRepository = BoardRepository(requireContext())
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)



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
            val boardData = boardDataList[position] // boardList는 BoardModel 객체의 리스트
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
        viewModel.getBoardData()
        viewModel.boardDataList.observe(viewLifecycleOwner) { boardDataList ->
            this.boardDataList.addAll(boardDataList)
            this.boardDataList.reverse()
            boardRVAdapter.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // 에러 처리
        }
    }

}


