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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.models.Team
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.databinding.FragmentHomeBinding
import com.example.pathfinder.ui.board.view.BoardInsideActivity
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.io.Serializable


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: BoardViewModel
    private lateinit var boardRVAdapterMentor: HomeRVAdapter
    private lateinit var boardRVAdapterMentee: HomeRVAdapter
    private val boardDataListMentor = mutableListOf<Board>()
    private val boardDataListMentee = mutableListOf<Board>()



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
        boardRVAdapterMentor = HomeRVAdapter(boardDataListMentor)
        boardRVAdapterMentee = HomeRVAdapter(boardDataListMentee)
        binding.mentorList.adapter = boardRVAdapterMentor
        binding.menteeList.adapter = boardRVAdapterMentee
        binding.mentorList.layoutManager = LinearLayoutManager(context)
        binding.menteeList.layoutManager = LinearLayoutManager(context)

    }

    private fun getFBBoardDataMentor() {
        viewModel.getBoardDataMentor()
        viewModel.boardDataListMentor.observe(viewLifecycleOwner) { boardDataListMentor ->
            this.boardDataListMentor.clear()
            this.boardDataListMentor.addAll(boardDataListMentor)
            this.boardDataListMentor.reverse()
            boardRVAdapterMentor.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
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

        viewModel.errorMessage.observe(viewLifecycleOwner) {
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