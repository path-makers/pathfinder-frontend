package com.example.pathfinder.ui.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Project
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.domain.usecases.GetUserBoardUseCase
import com.example.pathfinder.domain.usecases.GetUserProjectUseCase
import com.example.pathfinder.utils.FBAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserBoardUseCase: GetUserBoardUseCase,
    private val getUserProjectUseCase: GetUserProjectUseCase
): ViewModel() {
    private val _userBoardDataList = MutableStateFlow<Results<List<Board>>>(Results.Loading)
            val userBoardDataList = _userBoardDataList.asLiveData()
    private val _userProjectDataList = MutableStateFlow<Results<List<Project>>>(Results.Loading)
            val userProjectDataList = _userProjectDataList.asLiveData()

    private val userId = FBAuth.getUid()

    init {
    getUserBoardData(userId)
    getUserProjectData(userId)

    }

    fun getUserBoardData(userId:String) {
        viewModelScope.launch {
            getUserBoardUseCase(userId).collect { result ->
                when (result) {
                    is Results.Success -> {
                        _userBoardDataList.value = result
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }
                }
            }
        }
    }
   fun getUserProjectData(userId:String) {
        viewModelScope.launch {
            getUserProjectUseCase(userId).collect { result ->
                when (result) {
                    is Results.Success -> {
                        _userProjectDataList.value = result
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }
                }
            }
        }
    }


}