package com.example.pathfinder.ui.board.view.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.repository.BoardRepository

class BoardViewModel(private val boardRepository: BoardRepository) : ViewModel() {

    val boardDataList: MutableLiveData<List<Board>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun getBoardData() {
        boardRepository.getFBBoardData({
            boardDataList.value = it.reversed()
        }, { err ->
            errorMessage.value = err
        })
    }

    fun addBoard(title: String, content: String, uid: String, boardType: String, tags: List<String>) {
        val board = Board(
            title = title,
            content = content,
            uid = uid,
            date = "",
            boardType = boardType,
            tags = tags,
            comments = emptyList() //todo:코멘트
        )
        boardRepository.sendBoardData(board)
    }
}
