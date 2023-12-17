package com.example.pathfinder.ui.board.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import com.example.pathfinder.data.model.BoardRequest
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.domain.usecases.AddBoardUseCase
import com.example.pathfinder.domain.usecases.AddBoardCommentUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataByAlgorithmUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataByIdUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataMenteeUseCase
import com.example.pathfinder.domain.usecases.GetBoardDataMentorUseCase
import com.example.pathfinder.utils.FBAuth
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardDataMentorUseCase: GetBoardDataMentorUseCase,
    private val getBoardDataMenteeUseCase: GetBoardDataMenteeUseCase,
    private val getBoardDataByIdUseCase: GetBoardDataByIdUseCase,
    private val getBoardDataByAlgorithmUseCase: GetBoardDataByAlgorithmUseCase,
    private val addBoardCommentUseCase: AddBoardCommentUseCase,
    private val addBoardUseCase: AddBoardUseCase
//todo:check
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
            addBoardCommentUseCase(commentRequest,boardId).collect { result ->
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

    fun addBoard(title: String, content: String, uid: String, boardType: String, tags: List<String>) {
        val boardRequest = BoardRequest(
            title = title,
            content = content,
            uid = uid,
            boardType = boardType,
            tags = tags,
            author = FBAuth.getUserName()
        )
        viewModelScope.launch {
            addBoardUseCase(boardRequest).collect { result ->
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



}



