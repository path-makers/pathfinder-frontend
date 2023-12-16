package com.example.pathfinder.ui.board


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.databinding.FragmentBoardBinding
import com.example.pathfinder.ui.board.viewModel.BoardRefactorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardFragment : Fragment() {

    private val viewModel: BoardRefactorViewModel by viewModels()
    private lateinit var binding: FragmentBoardBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val boardDataList = mutableListOf<Board>()
    private lateinit var boardRVAdapter: BoardRVAdapter
    private var currentBoardType = "MENTOR"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        binding.mentorBtn.isChecked = true //첫 진입시 멘토

        initBoardRecyclerView()
        initWriteButton()

        return binding.root
    }//뷰 생성과 초기화


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            getBoardData()
            swipeRefreshLayout.isRefreshing = false
        }//pull to refresh 구현


        subscribeToDataChanges(currentBoardType)

    }//뷰가 생성된 후 데이터를 연결

    private fun initBoardRecyclerView() {
        boardRVAdapter = BoardRVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter
        binding.boardListView.layoutManager = LinearLayoutManager(context)

        val updateButtonState = { mentorSelected: Boolean ->
            binding.mentorBtn.isChecked = mentorSelected
            binding.menteeBtn.isChecked = !mentorSelected

            binding.mentorBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (mentorSelected) R.color.black else R.color.gray
                )
            )
            binding.menteeBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (mentorSelected) R.color.gray else R.color.black
                )
            )

            subscribeToDataChanges(if (mentorSelected) "MENTOR" else "MENTEE")
        }

        binding.mentorBtn.setOnClickListener {
            if (currentBoardType != "MENTOR") {
                currentBoardType = "MENTOR"
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

    private val startWriteActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                Handler(Looper.getMainLooper()).postDelayed({
                    //서버와 프론트 시간 차이로 인해 글 작성후 업데이트 전 0.1초 딜레이
                    getBoardData()
                    subscribeToDataChanges(currentBoardType)
                }, 100)
            }
        }//글 작성후 업데이트


    private fun getBoardData() {
        viewModel.getBoardDataMentor()
        viewModel.getBoardDataMentee()

    }//데이터 가져오기

    private val observer = { results: Results<List<Board>> ->
        when (results) {
            is Results.Loading -> {
                // 로딩 처리
            }

            is Results.Success -> {
                updateUI(results.data)
            }

            is Results.Failure -> {
                // 오류 처리
            }
        }
    }//데이터 변경 관찰자

    private fun subscribeToDataChanges(boardType: String) {
        viewModel.boardDataListMentor.removeObservers(viewLifecycleOwner)
        viewModel.boardDataListMentee.removeObservers(viewLifecycleOwner)
        //이전에 연결된 데이터 변경 관찰자 제거
        if (boardType == "MENTOR") {
            viewModel.boardDataListMentor.observe(viewLifecycleOwner, observer)
        } else {
            viewModel.boardDataListMentee.observe(viewLifecycleOwner, observer)
        }//라이브 데이터에 관찰자 등록
    }


    private fun updateUI(newDataList: List<Board>) {
        boardDataList.clear()
        boardDataList.addAll(newDataList)
        boardRVAdapter.notifyDataSetChanged()
    }//리사이클러뷰 업데이트

}


