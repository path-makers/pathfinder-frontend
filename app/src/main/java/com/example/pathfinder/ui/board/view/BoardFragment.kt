package com.example.pathfinder.ui.board.view

import BoardRVAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.databinding.FragmentBoardBinding
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel


class BoardFragment : Fragment() {
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding: FragmentBoardBinding

    private val boardDataList = mutableListOf<Board>()
    private lateinit var boardRVAdapter: BoardRVAdapter

    private val startWriteActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            getFBBoardData("MENTOR")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        binding.mentorBtn.isChecked = true
        val boardRepository = BoardRepository(requireContext())
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)



        initBoardListView()
        initWriteButton()
        getFBBoardData("MENTOR")

        return binding.root
    }

    private fun initBoardListView() {
        boardRVAdapter = BoardRVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter
        binding.boardListView.layoutManager = LinearLayoutManager(context)

        binding.mentorBtn.setOnClickListener {

            binding.mentorBtn.isChecked = true
            binding.menteeBtn.isChecked = false

            binding.mentorBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.menteeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            getFBBoardData("MENTOR")
        }

        binding.menteeBtn.setOnClickListener {

            binding.menteeBtn.isChecked = true
            binding.mentorBtn.isChecked = false

            binding.menteeBtn.setTextColor(ContextCompat.getColor(requireContext(),  R.color.black))
            binding.mentorBtn.setTextColor(ContextCompat.getColor(requireContext(),  R.color.gray))

            getFBBoardData("MENTEE")
        }


    }

    private fun initWriteButton() {
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startWriteActivityForResult.launch(intent)
        }
    }



    private fun getFBBoardData(boardType: String) {
        viewModel.getBoardData(boardType)
        viewModel.boardDataList.observe(viewLifecycleOwner) { boardDataList ->
            this.boardDataList.clear()
            this.boardDataList.addAll(boardDataList)
            this.boardDataList.reverse()
            boardRVAdapter.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // 에러 처리
        }
    }

}


