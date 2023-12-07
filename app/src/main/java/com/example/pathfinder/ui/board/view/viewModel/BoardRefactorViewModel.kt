package com.example.pathfinder.ui.board.view.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRefactorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import com.example.pathfinder.domain.usecases.GetBoardDataByAlgorithmUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataByIdUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataMentorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class BoardRefactorViewModel @Inject constructor(
    private val boardRefactorRepository: BoardRefactorRepository,
    private val getBoardDataMentorUseCase: GetBoardDataMentorUseCase,
    private val getBoardDataMenteeUseCase: GetBoardDataMentorUseCase,
    private val getBoardDataByIdUseCase: GetBoardDataByIdUseCase,
    private val getBoardDataByAlgorithmUseCase: GetBoardDataByAlgorithmUseCase

) :
    ViewModel() {

    private val _boardDataListMentor = MutableStateFlow<Results<List<Board>>>(Results.Loading)
    val boardDataListMentor = _boardDataListMentor.asLiveData()

    private val _boardDataListMentee = MutableStateFlow<Results<List<Board>>>(Results.Loading)
    val boardDataListMentee = _boardDataListMentee.asLiveData()

    private val _boardDataListById = MutableStateFlow<Results<Board>>(Results.Loading)
    val boardDataListById = _boardDataListById.asLiveData()

    private val _boardDataListAlgorithm = MutableStateFlow<Results<List<Board>>>(Results.Loading)
    val boardDataListAlgorithm = _boardDataListAlgorithm.asLiveData()

    init {
        getBoardDataMentor()
        getBoardDataMentee()

    }


    fun getBoardDataMentor() {
        viewModelScope.launch {
            getBoardDataMentorUseCase().collect { result ->
                when (result) {
                    is Results.Success -> {
                        _boardDataListMentor.value = result
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }
                }
            }
        }
    }
    fun getBoardDataMentee() {
        viewModelScope.launch {
            getBoardDataMenteeUseCase().collect { result ->
                when (result) {
                    is Results.Success -> {
                        _boardDataListMentee.value = result
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }
                }
            }
        }
    }


    fun getBoardDataById(boardId: String) {
        viewModelScope.launch {
           getBoardDataByIdUseCase(boardId).collect { result ->
                when (result) {
                    is Results.Success -> {
                        _boardDataListById.value = result
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }
                }
            }
        }
    }
    fun getBoardDataByAlgorithm(userId: String) {
        viewModelScope.launch {
            getBoardDataByAlgorithmUseCase(userId).collect { result ->
                when (result) {
                    is Results.Success -> {
                        _boardDataListAlgorithm.value = result
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



