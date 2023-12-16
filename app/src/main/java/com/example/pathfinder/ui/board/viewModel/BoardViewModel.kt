package com.example.pathfinder.ui.board.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.repository.BoardRepository

class BoardViewModel(private val boardRepository: BoardRepository) : ViewModel() {

    val boardDataList: MutableLiveData<List<Board>> = MutableLiveData()
    val singleBoardData: MutableLiveData<Board> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val commentsData: MutableLiveData<List<Comment>> = MutableLiveData()
    val boardDataListMentor: MutableLiveData<List<Board>> = MutableLiveData()
    val boardDataListMentee: MutableLiveData<List<Board>> = MutableLiveData()
    val boardDataListAlgorithm: MutableLiveData<List<Board>> = MutableLiveData()


    fun getBoardDataMentor() {
        boardRepository.getBoardDataByType("MENTOR", {
            boardDataListMentor.value = it.reversed()
        }, { err ->
            errorMessage.value = err
        })
    }

    fun getBoardDataMentee() {
        boardRepository.getBoardDataByType("MENTEE", {
            boardDataListMentee.value = it.reversed()
        }, { err ->
            errorMessage.value = err
        })
    }


    fun getBoardDataById(boardId: String) {
        boardRepository.getBoardDataById(boardId, { board->
            singleBoardData.value = board
        }, { err ->
            errorMessage.value = err
        })
    }

    fun getBoardDataByAlgorithm(userId: String) {
        boardRepository.getBoardDataByAlgorithm(userId, {
            boardDataListAlgorithm.value = it.reversed()
        }, { err ->
            errorMessage.value = err
        })
    }



    fun addBoard(title: String, content: String, uid: String, boardType: String, tags: List<String>) {
        val board = Board(
            title = title,
            content = content,
            uid = uid,
            createdAt = "",
            boardType = boardType,
            tags = tags,
            comments = emptyList() //todo:코멘트
        )
        boardRepository.sendBoardData(board)
    }
    fun addComment(uid: String, content: String, boardId: String,author:String) {
        val comment = Comment(
            uid = uid,
            content = content,
            author = author,
        )
        boardRepository.sendCommentData(comment, boardId)
        { success ->
            if (success) {
                getBoardDataById(boardId)
            }
        }
    }
}
