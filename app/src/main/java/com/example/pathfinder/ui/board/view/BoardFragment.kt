package com.example.pathfinder.ui.board.view

import BoardRecyclerViewAdapter
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val mentorBoardDataList = mutableListOf<Board>()
    private val menteeBoardDataList = mutableListOf<Board>()
    private var mentorBoardObserver: Observer<List<Board>>? = null
    private var menteeBoardObserver: Observer<List<Board>>? = null
    private var currentBoardType = "MENTOR"

    private val boardDataList = mutableListOf<Board>()
    private lateinit var boardRVAdapter: BoardRecyclerViewAdapter

    private val startWriteActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            getFBBoardData(currentBoardType)
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
        boardRVAdapter = BoardRecyclerViewAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter
        binding.boardListView.layoutManager = LinearLayoutManager(context)

        binding.mentorBtn.setOnClickListener {
            switchBoardType("MENTOR")
        }

        binding.menteeBtn.setOnClickListener {
            switchBoardType("MENTEE")
        }


    }

    private fun switchBoardType(boardType: String) {
        if (currentBoardType != boardType) {
            currentBoardType = boardType
            when (boardType) {
                "MENTOR" -> {
                    updateUI(mentorBoardDataList)
                }
                "MENTEE" -> {
                    if (menteeBoardDataList.isEmpty()) {
                        getFBBoardData("MENTEE")
                    } else {
                        updateUI(menteeBoardDataList)
                    }
                }
            }
        }

        updateButtonStyles(boardType)
    }

    private fun updateUI(boardList: List<Board>) {
        boardDataList.clear()
        boardDataList.addAll(boardList)
        boardRVAdapter.notifyDataSetChanged()
    }

    private fun updateButtonStyles(boardType: String) {
        val isMentor = boardType == "MENTOR"
        binding.mentorBtn.isChecked = isMentor
        binding.menteeBtn.isChecked = !isMentor

        binding.mentorBtn.setTextColor(ContextCompat.getColor(requireContext(), if (isMentor) R.color.black else R.color.gray))
        binding.menteeBtn.setTextColor(ContextCompat.getColor(requireContext(), if (isMentor) R.color.gray else R.color.black))
    }

    private fun initWriteButton() {
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startWriteActivityForResult.launch(intent)
        }
    }



    private fun getFBBoardData(boardType: String) {
        // 관찰자를 먼저 삭제
        mentorBoardObserver?.let { viewModel.boardDataList.removeObserver(it) }
        menteeBoardObserver?.let { viewModel.boardDataList.removeObserver(it) }
        viewModel.getBoardData(boardType)
        // 새 관찰자를 생성
        val boardObserver = Observer<List<Board>> { newData ->
            val targetList = if (boardType == "MENTOR") mentorBoardDataList else menteeBoardDataList
            targetList.clear()
            targetList.addAll(newData.reversed())
            if (currentBoardType == boardType) {
                // 현재 보고 있는 타입이면 UI를 업데이트
                updateUI(targetList)
            }
        }

        // 관찰자를 적절한 변수에 저장
        if (boardType == "MENTOR") {
            mentorBoardObserver = boardObserver
        } else {
            menteeBoardObserver = boardObserver
        }

        // LiveData 관찰자를 등록
        viewModel.boardDataList.observe(viewLifecycleOwner, boardObserver)
    }

}


