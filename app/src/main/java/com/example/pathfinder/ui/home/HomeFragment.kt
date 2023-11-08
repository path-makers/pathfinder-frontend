package com.example.pathfinder.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.pathfinder.R
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.databinding.FragmentHomeBinding
import com.example.pathfinder.ui.board.view.BoardInsideActivity
import com.example.pathfinder.ui.board.view.BoardListLVAdapter
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModel
import com.example.pathfinder.ui.board.view.viewModel.BoardViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable


class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentHomeBinding
    private var uid: String? = null

    private lateinit var viewModel: BoardViewModel

    private val boardDataList_mentor = mutableListOf<Board>()
    private val boardDataList_mentee = mutableListOf<Board>()
    private lateinit var boardRVAdapter_mentor: HomeListLVAdapter2
    private lateinit var boardRVAdapter_mentee: HomeListLVAdapter2_mentee


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            uid = firebaseUser.uid
            database = FirebaseDatabase.getInstance().getReference("exerciseRecords/$uid")
        } else {
            // No user is signed in
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.chatButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_chatFragment)
        }

        val boardRepository = BoardRepository(requireContext())
        val viewModelFactory = BoardViewModelFactory(boardRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)

        initBoardListView()
        getFBBoardData_mentor("MENTOR")
        getFBBoardData_mentee("MENTEE")

        return binding.root
    }

    private fun initBoardListView() {
        boardRVAdapter_mentor = HomeListLVAdapter2(boardDataList_mentor)
        boardRVAdapter_mentee = HomeListLVAdapter2_mentee(boardDataList_mentee)
        binding.mentorList.adapter = boardRVAdapter_mentor
        binding.menteeList.adapter = boardRVAdapter_mentee
        binding.teamList.adapter = boardRVAdapter_mentor


        // 더보기 -> 해당 fragment로 이동
        binding.mentorTextViewMore.setOnClickListener {

        }
        binding.menteeTextViewMore.setOnClickListener {

        }
        binding.teamTextViewMore.setOnClickListener {

        }

        // 글 클릭 -> 해당 글 페이지로 이동
        binding.mentorList.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, BoardInsideActivity::class.java)
            val boardData = boardDataList_mentor[position] // boardList는 BoardModel 객체의 리스트
            intent.putExtra("boardData", boardData as Serializable)
            startActivity(intent)
        }
        binding.menteeList.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, BoardInsideActivity::class.java)
            val boardData = boardDataList_mentee[position] // boardList는 BoardModel 객체의 리스트
            intent.putExtra("boardData", boardData as Serializable)
            startActivity(intent)
        }
        // team
    }

    private fun getFBBoardData_mentor(boardType: String) {
        viewModel.getBoardData(boardType)
        viewModel.boardDataList.observe(viewLifecycleOwner) { boardDataList ->
            this.boardDataList_mentor.clear()
            this.boardDataList_mentor.addAll(boardDataList)
            this.boardDataList_mentor.reverse()
            boardRVAdapter_mentor.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // 에러 처리
        }
    }

    private fun getFBBoardData_mentee(boardType: String) {
        viewModel.getBoardData(boardType)
        viewModel.boardDataList.observe(viewLifecycleOwner) { boardDataList ->
            this.boardDataList_mentee.clear()
            this.boardDataList_mentee.addAll(boardDataList)
            this.boardDataList_mentee.reverse()
            boardRVAdapter_mentee.notifyDataSetChanged()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // 에러 처리
        }
    }


}