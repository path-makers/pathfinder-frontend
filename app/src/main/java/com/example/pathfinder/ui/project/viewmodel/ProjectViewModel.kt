package com.example.pathfinder.ui.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Project
import com.example.pathfinder.domain.usecases.AddProjectCommentUseCase
import com.example.pathfinder.domain.usecases.AddProjectUseCase
import com.example.pathfinder.domain.usecases.GetProjectDataUseCase
import com.example.pathfinder.domain.usecases.GetSingleProjectDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val getProjectDataUseCase: GetProjectDataUseCase,
    private val getSingleProjectDataUseCase: GetSingleProjectDataUseCase,
    private val addProjectCommentUseCase: AddProjectCommentUseCase,
    private val addProjectUseCase: AddProjectUseCase
) :
    ViewModel() {

        private val _projectDataList = MutableStateFlow<Results<List<Project>>>(Results.Loading)
        val projectDataList = _projectDataList.asLiveData()
        private val _singleProjectDataList = MutableStateFlow<Results<Project>>(Results.Loading)
        val singleProjectDataList = _singleProjectDataList.asLiveData()

    init {
            getProjectData()
        }

    fun getProjectData() {
            viewModelScope.launch {
                getProjectDataUseCase().collect { result ->
                    when (result) {
                        is Results.Success -> {
                            _projectDataList.value = result
                        }
                        is Results.Loading -> {
                        }
                        is Results.Failure -> {
                        }

                        else -> {}
                    }
                }
            }
        }

    fun getSingleProjectData(teamId:String) {
        viewModelScope.launch {
            getSingleProjectDataUseCase(teamId).collect { result ->
                when (result) {
                    is Results.Success -> {
                        _singleProjectDataList.value = result
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }

                    else -> {}
                }
            }
        }
    }

    fun addComment(commentRequest: CommentRequest, projectId: String) {
        viewModelScope.launch {
            addProjectCommentUseCase(commentRequest,projectId).collect { result ->
                when (result) {
                    is Results.Success -> {
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }

                    else -> {}
                }
            }
        }
    }

    fun addProject(projectRequest: ProjectRequest) {
        viewModelScope.launch {
            addProjectUseCase(projectRequest).collect { result ->
                when (result) {
                    is Results.Success -> {
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }

                    else -> {}
                }
            }
        }
    }







}