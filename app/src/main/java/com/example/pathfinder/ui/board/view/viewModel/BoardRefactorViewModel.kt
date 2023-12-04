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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class BoardRefactorViewModel @Inject constructor(
    private val boardRefactorRepository: BoardRefactorRepository
) :
    ViewModel() {

    private val _boardDataListMentor = MutableStateFlow<Results<List<Board>>>(Results.Loading)
    val boardDataListMentor = _boardDataListMentor.asLiveData()

    private val _boardDataListMentee = MutableStateFlow<Results<List<Board>>>(Results.Loading)
    val boardDataListMentee = _boardDataListMentee.asLiveData()

    init {
        getBoardDataMentor()
        getBoardDataMentee()
    }


    fun getBoardDataMentor() {
        viewModelScope.launch {
            boardRefactorRepository.getBoardDataByType("MENTOR").collect { result ->
                _boardDataListMentor.value = result
            }
        }
    }

    fun getBoardDataMentee() {
        viewModelScope.launch {
            boardRefactorRepository.getBoardDataByType("MENTEE").collect { result ->
                _boardDataListMentee.value = result
            }
        }
    }




}



