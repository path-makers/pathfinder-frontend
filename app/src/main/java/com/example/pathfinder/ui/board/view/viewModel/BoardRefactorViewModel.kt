package com.example.pathfinder.ui.board.view.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.models.Results
import com.example.pathfinder.data.repository.BoardRefactorRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

val boardDataListMentor: MutableLiveData<List<Board>?> = MutableLiveData()
@HiltViewModel
class BoardRefactorViewModel @Inject constructor(

    private val boardRefactorRepository: BoardRefactorRepository,

    savedStateHandle: SavedStateHandle
) : ViewModel() {





    private fun getBoardDataMentor() {
        viewModelScope.launch {
            boardRefactorRepository.getBoardDataByType("mentor").collect { result ->
                when (result) {
                    is Results.Success -> {
                        boardDataListMentor.value = result.value
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


