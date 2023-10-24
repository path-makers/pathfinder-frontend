package com.example.pathfinder.ui.board.view.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pathfinder.data.repository.BoardRepository

@Suppress("UNCHECKED_CAST")
class BoardViewModelFactory(private val boardRepository: BoardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BoardViewModel(boardRepository) as T
    }
}

