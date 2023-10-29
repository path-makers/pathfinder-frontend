package com.example.pathfinder.ui.board.view.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.models.Comment
import com.example.pathfinder.data.repository.BoardRepository

class BoardViewModel(private val boardRepository: BoardRepository) : ViewModel() {

    val boardDataList: MutableLiveData<List<Board>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val commentsData: MutableLiveData<List<Comment>> = MutableLiveData()

    fun getBoardData(boardType: String) {
        boardRepository.getFBBoardData(boardType, {
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
    fun addComment(uid: String, content: String, boardId: String) {
        val comment = Comment(
            uid = uid,
            content = content,

        )
        Log.d("BoardViewModel", "addComment: $boardId")
        boardRepository.sendCommentData(comment, boardId)
        { success ->
            if (success) {

                val updatedComments = commentsData.value.orEmpty() + comment
                commentsData.postValue(updatedComments)
            }
        }
    }
}
