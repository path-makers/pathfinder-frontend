package com.example.pathfinder.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.databinding.FragmentHomeBinding
import com.example.pathfinder.ui.board.view.BoardInsideActivity
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

import java.io.Serializable


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding


    private lateinit var viewModel: BoardViewModel

    private val boardDataListMentor = mutableListOf<Board>()
    private val boardDataListMentee = mutableListOf<Board>()
    private lateinit var boardRVAdapterMentor: HomeListLVAdapter2
    private lateinit var boardRVAdapterMentee: HomeListLVAdapter2_mentee


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.chatButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_aiIntroFragment)
        }

        val boardRepository = BoardRepository(requireContext())
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[BoardViewModel::class.java]

        initBoardListView()
        getFBBoardDataMentor()
        getFBBoardDataMentee()

        hideBottomNavigation(false);

        return binding.root
    }

    private fun initBoardListView() {
        boardRVAdapterMentor = HomeListLVAdapter2(boardDataListMentor)
        boardRVAdapterMentee = HomeListLVAdapter2_mentee(boardDataListMentee)
        binding.mentorList.adapter = boardRVAdapterMentor
        binding.menteeList.adapter = boardRVAdapterMentee
        binding.teamList.adapter = boardRVAdapterMentor


        // 더보기 -> 해당 fragment로 이동
        binding.mentorTextViewMore.setOnClickListener {

        }
        binding.menteeTextViewMore.setOnClickListener {

        }
        binding.teamTextViewMore.setOnClickListener {

        }

        // 글 클릭 -> 해당 글 페이지로 이동
        binding.mentorList.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(context, BoardInsideActivity::class.java)
            val boardData = boardDataListMentor[position] // boardList는 BoardModel 객체의 리스트
            intent.putExtra("boardData", boardData as Serializable)
            startActivity(intent)
        }
        binding.menteeList.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(context, BoardInsideActivity::class.java)
            val boardData = boardDataListMentee[position] // boardList는 BoardModel 객체의 리스트
            intent.putExtra("boardData", boardData as Serializable)
            startActivity(intent)
        }
        // team
    }

    private fun getFBBoardDataMentor() {
        viewModel.getBoardDataMentor()
        viewModel.boardDataListMentor.observe(viewLifecycleOwner) { boardDataListMentor ->
            this.boardDataListMentor.clear()
            this.boardDataListMentor.addAll(boardDataListMentor)
            this.boardDataListMentor.reverse()
            boardRVAdapterMentor.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // 에러 처리
        }
    }

    private fun getFBBoardDataMentee() {
        viewModel.getBoardDataMentee()
        viewModel.boardDataListMentee.observe(viewLifecycleOwner) { boardDataListMentee ->
            this.boardDataListMentee.clear()
            this.boardDataListMentee.addAll(boardDataListMentee)
            this.boardDataListMentee.reverse()
            boardRVAdapterMentee.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // 에러 처리
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