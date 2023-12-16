package com.example.pathfinder.ui.board.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRefactorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.domain.usecases.AddCommentUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataByAlgorithmUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataByIdUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataMentorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class BoardRefactorViewModel @Inject constructor(
    private val getBoardDataMentorUseCase: GetBoardDataMentorUseCase,
    private val getBoardDataMenteeUseCase: GetBoardDataMentorUseCase,
    private val getBoardDataByIdUseCase: GetBoardDataByIdUseCase,
    private val getBoardDataByAlgorithmUseCase: GetBoardDataByAlgorithmUseCase,
    private val addCommentUseCase: AddCommentUseCase,


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

    fun addComment(uid: String, content: String, boardId: String,author:String) {
        val commentRequest = CommentRequest(
            uid = uid,
            content = content,
            author = author,
        )

        viewModelScope.launch {
            addCommentUseCase(commentRequest,boardId).collect { result ->
                when (result) {
                    is Results.Success -> {
                    }
                    is Results.Loading -> {
                    }
                    is Results.Failure -> {
                    }
                }
            }
        }

    }





//    fun addBoard(title: String, content: String, uid: String, boardType: String, tags: List<String>) {
//        val board = Board(
//            title = title,
//            content = content,
//            uid = uid,
//            date = "",
//            boardType = boardType,
//            tags = tags,
//            comments = emptyList() //todo:코멘트
//        )
//        boardRepository.sendBoardData(board)
//    }






}



