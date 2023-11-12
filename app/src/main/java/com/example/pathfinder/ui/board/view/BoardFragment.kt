package com.example.pathfinder.ui.board.view

import BoardRVAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
    private var currentBoardType = "MENTOR"

    private val startWriteActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                Handler(Looper.getMainLooper()).postDelayed({
                    //서버와 프론트 시간 차이로 인해 글 작성후 업데이트 전 0.1초 딜레이
                    getBoardData()
                    subscribeToDataChanges(currentBoardType)
                }, 100)
                Log.d("BoardFragment", "onCreateView: ${viewModel.boardDataList.value}}")
            }
        }//글 작성후 업데이트


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        binding.mentorBtn.isChecked = true
        val boardRepository = BoardRepository(requireContext())
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[BoardViewModel::class.java]


        initBoardRecyclerView()
        initWriteButton()


        return binding.root
    }//뷰 생성과 초기화


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.boardDataListMentor.value == null) {
            getBoardData()
        }
        subscribeToDataChanges(currentBoardType)
    }//뷰가 생성된 후 데이터를 연결

    private fun initBoardRecyclerView() {
        boardRVAdapter = BoardRVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter
        binding.boardListView.layoutManager = LinearLayoutManager(context)

        val updateButtonState = { mentorSelected: Boolean ->
            binding.mentorBtn.isChecked = mentorSelected
            binding.menteeBtn.isChecked = !mentorSelected

            binding.mentorBtn.setTextColor(ContextCompat.getColor(requireContext(), if (mentorSelected) R.color.black else R.color.gray))
            binding.menteeBtn.setTextColor(ContextCompat.getColor(requireContext(), if (mentorSelected) R.color.gray else R.color.black))

            subscribeToDataChanges(if (mentorSelected) "MENTOR" else "MENTEE")
        }

        binding.mentorBtn.setOnClickListener {
            if (currentBoardType != "MENTOR") { currentBoardType = "MENTOR"
                updateButtonState(true)
            }
        }

        binding.menteeBtn.setOnClickListener {
            if (currentBoardType != "MENTEE") {
                currentBoardType = "MENTEE"
                updateButtonState(false)
            }
        }


    }//리사이클러뷰 초기화,버튼 상태 변경

    private fun initWriteButton() {
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startWriteActivityForResult.launch(intent)
        }
    }//글 작성


    private fun getBoardData() {

        viewModel.getBoardDataMentor()
        viewModel.getBoardDataMentee()

    }//데이터 가져오기

    private fun subscribeToDataChanges(boardType: String) {
        viewModel.boardDataListMentor.removeObservers(viewLifecycleOwner)
        viewModel.boardDataListMentee.removeObservers(viewLifecycleOwner)

        if (boardType == "MENTOR") {
            viewModel.boardDataListMentor.observe(viewLifecycleOwner) { boardDataList ->
                updateUI(boardDataList)
            }
        } else {
            viewModel.boardDataListMentee.observe(viewLifecycleOwner) { boardDataList ->
                updateUI(boardDataList)
            }
        }

    }//데이터 변경시에 불려와서 리사이클러뷰 업데이트, 중복 구독 안되게 관찰자 제거해줘야 함

    private fun updateUI(boardDataList: List<Board>) {
        this.boardDataList.clear()
        this.boardDataList.addAll(boardDataList)
        this.boardDataList.reverse()
        boardRVAdapter.notifyDataSetChanged()
    }//리사이클러뷰 업데이트


}


