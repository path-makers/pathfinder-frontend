package com.example.pathfinder.ui.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.databinding.FragmentBoardBinding
import com.example.pathfinder.databinding.FragmentUserWrittenBoardBinding
import com.example.pathfinder.ui.board.BoardRVAdapter
import com.example.pathfinder.utils.FBAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserWrittenPostsFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var binding: FragmentUserWrittenBoardBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val boardDataList = mutableListOf<Board>()
    private lateinit var boardRVAdapter: BoardRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_written_board, container, false)
        boardRVAdapter = BoardRVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter
        binding.boardListView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }//뷰 생성과 초기화


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            getBoardData()
            swipeRefreshLayout.isRefreshing = false
        }//pull to refresh 구현

        viewModel.userBoardDataList.observe(viewLifecycleOwner, observer)
    }//뷰가 생성된 후 데이터를 연결


    private fun getBoardData() {
        viewModel.getUserBoardData(FBAuth.getUid())

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



    private fun updateUI(newDataList: List<Board>) {
        boardDataList.clear()
        boardDataList.addAll(newDataList)
        boardRVAdapter.notifyDataSetChanged()
    }//리사이클러뷰 업데이트

}


