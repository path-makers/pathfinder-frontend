package com.example.pathfinder.ui.home


import HomeRVAlgorithmAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.databinding.FragmentHomeBinding
import com.example.pathfinder.ui.board.viewModel.BoardRefactorViewModel
import com.example.pathfinder.utils.FBAuth

import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private val viewModel: BoardRefactorViewModel by viewModels()
    private lateinit var boardRVAdapterMentor: HomeRVAdapter
    private lateinit var boardRVAdapterMentee: HomeRVAdapter
    private lateinit var boardRVAdapterAlgorithm: HomeRVAlgorithmAdapter
    private val boardDataListMentor = mutableListOf<Board>()
    private val boardDataListMentee = mutableListOf<Board>()
    private val boardDataListAlgorithm = mutableListOf<Board>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initButtons()
        initBoardRecyclerView()
        hideBottomNavigation(false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBoardDataByAlgorithm(FBAuth.getUid())

        viewModel.boardDataListMentor.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> {
                    // 로딩 UI 처리
                }

                is Results.Success -> {
                    val teamData = result.data
                    // 성공적으로 데이터를 받았을 때 UI 업데이트
                    updateMentor(teamData)
                }

                is Results.Failure -> {
                    // 오류 UI 처리
                }
            }
        }

        viewModel.boardDataListMentee.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> {
                    // 로딩 UI 처리
                }

                is Results.Success -> {
                    val teamData = result.data
                    // 성공적으로 데이터를 받았을 때 UI 업데이트
                    updateMentee(teamData)
                }

                is Results.Failure -> {
                    // 오류 UI 처리
                }
            }
        }

        viewModel.boardDataListAlgorithm.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> {
                    // 로딩 UI 처리
                }

                is Results.Success -> {
                    val teamData = result.data
                    // 성공적으로 데이터를 받았을 때 UI 업데이트
                    updateAlgorithm(teamData)
                }

                is Results.Failure -> {
                    // 오류 UI 처리
                }
            }
        }

    }

    private fun initButtons() {
        binding.chatButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_aiIntroFragment)
        }
        binding.recommendTextViewMore.setOnClickListener {
            (requireActivity().findViewById(R.id.bottomNavigationView) as BottomNavigationView)
                .selectedItemId = R.id.boardFragment
        }
        binding.mentorTextViewMore.setOnClickListener {
            (requireActivity().findViewById(R.id.bottomNavigationView) as BottomNavigationView)
                .selectedItemId = R.id.boardFragment
        }
        binding.menteeTextViewMore.setOnClickListener {
            (requireActivity().findViewById(R.id.bottomNavigationView) as BottomNavigationView)
                .selectedItemId = R.id.boardFragment
        }
    }


    private fun initBoardRecyclerView() {
        boardRVAdapterMentor = HomeRVAdapter(boardDataListMentor)
        boardRVAdapterMentee = HomeRVAdapter(boardDataListMentee)
        boardRVAdapterAlgorithm = HomeRVAlgorithmAdapter(boardDataListAlgorithm)
        binding.mentorList.adapter = boardRVAdapterMentor
        binding.menteeList.adapter = boardRVAdapterMentee
        binding.recommendList.adapter = boardRVAdapterAlgorithm
        binding.mentorList.layoutManager = LinearLayoutManager(context)
        binding.menteeList.layoutManager = LinearLayoutManager(context)
        binding.recommendList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }


    private fun updateMentor(teamData: List<Board>) {

        viewModel.boardDataListMentor.observe(viewLifecycleOwner) { boardDataListMentor ->
            this.boardDataListMentor.clear()
            this.boardDataListMentor.addAll(teamData)
            boardRVAdapterMentor.notifyDataSetChanged()
        }


    }

    private fun updateMentee(teamData: List<Board>) {

        viewModel.boardDataListMentee.observe(viewLifecycleOwner) { boardDataListMentee ->
            this.boardDataListMentee.clear()
            this.boardDataListMentee.addAll(teamData)
            boardRVAdapterMentee.notifyDataSetChanged()
        }


    }

    private fun updateAlgorithm(teamData: List<Board>) {

        viewModel.boardDataListAlgorithm.observe(viewLifecycleOwner) { boardDataListAlgorithm ->
            this.boardDataListAlgorithm.clear()
            this.boardDataListAlgorithm.addAll(teamData)
            this.boardDataListAlgorithm.reverse()
            boardRVAdapterAlgorithm.notifyDataSetChanged()
        }


    }

    fun hideBottomNavigation(bool: Boolean) {
        val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (bool)
            bottomNavigation.visibility = View.GONE
        else
            bottomNavigation.visibility = View.VISIBLE
    }


}