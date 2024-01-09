package com.example.pathfinder.ui.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.databinding.FragmentProjectBinding
import com.example.pathfinder.data.model.Project
import com.example.pathfinder.ui.project.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectFragment : Fragment() {

    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var binding: FragmentProjectBinding
    private lateinit var projectRVAdapter: ProjectRVAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        initProjectRecyclerView()
        initWriteButton()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            getProjectData()
            swipeRefreshLayout.isRefreshing = false
        }//pull to refresh 구현
    }//뷰가 생성된 후 데이터를 연결

    private fun initProjectRecyclerView() {
        viewModel.projectDataList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Success -> {
                    projectRVAdapter = ProjectRVAdapter(result.data as MutableList<Project>)
                    binding.projectRecyclerView.adapter = projectRVAdapter
                }

                is Results.Loading -> {
                    // 로딩 처리
                }

                is Results.Failure -> {
                    // 오류 처리
                }
            }
        }
        binding.projectRecyclerView.layoutManager = LinearLayoutManager(context)
    }


    private fun initWriteButton() {
        binding.teamWriteBtn.setOnClickListener {
            val intent = Intent(context, ProjectWriteActivity::class.java)
            startWriteActivityForResult.launch(intent)
        }
    }

    private val startWriteActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Handler(Looper.getMainLooper()).postDelayed({
                    //서버와 프론트 시간 차이로 인해 글 작성후 업데이트 전 0.1초 딜레이
                   getProjectData()
                }, 500)
            }
        }

    private fun getProjectData() {
        viewModel.getProjectData()
    }


}

