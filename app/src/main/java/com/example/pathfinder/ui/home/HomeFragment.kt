package com.example.pathfinder.ui.home


import HomeRVAlgorithmAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.models.Team
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.databinding.FragmentHomeBinding
import com.example.pathfinder.ui.board.view.BoardInsideActivity
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory
import com.example.pathfinder.utils.FBAuth

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.io.Serializable


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: BoardViewModel
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

        val boardRepository = BoardRepository(requireContext())
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[BoardViewModel::class.java]

        initBoardRecyclerView()
        hideBottomNavigation(false);


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.boardDataListMentor.value == null) {
            getBoardData()

        }


        subscribeToData()
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
        binding.recommendList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun getBoardData() {

        viewModel.getBoardDataMentor()
        viewModel.getBoardDataMentee()
        viewModel.getBoardDataByAlgorithm(FBAuth.getUid())
    }

    private fun subscribeToData() {

        viewModel.boardDataListMentor.observe(viewLifecycleOwner) { boardDataListMentor ->
            this.boardDataListMentor.clear()
            this.boardDataListMentor.addAll(boardDataListMentor)
            this.boardDataListMentor.reverse()
            boardRVAdapterMentor.notifyDataSetChanged()
        }
        viewModel.boardDataListMentee.observe(viewLifecycleOwner) { boardDataListMentee ->
            this.boardDataListMentee.clear()
            this.boardDataListMentee.addAll(boardDataListMentee)
            this.boardDataListMentee.reverse()
            boardRVAdapterMentee.notifyDataSetChanged()

        }


        viewModel.boardDataListAlgorithm.observe(viewLifecycleOwner) { boardDataListAlgorithm ->
            this.boardDataListAlgorithm.clear()
            this.boardDataListAlgorithm.addAll(boardDataListAlgorithm)
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