package com.example.pathfinder.data.source.remote.board

import com.example.pathfinder.data.di.IoDispatcher
import com.example.pathfinder.data.model.BoardRequest
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.response.BoardResponse
import com.example.pathfinder.data.response.BoardSingleResponse

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class BoardDetailRemoteDataSource @Inject constructor(
    private val boardApi: BoardApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {


    suspend fun getBoardDataByType(boardType: String): Response<BoardResponse> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.getBoardDataByType(boardType)

        }
    }

    suspend fun getBoardDataById(boardId: String): Response<BoardSingleResponse> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.getBoardDataById(boardId)
        }
    }

    suspend fun getBoardDataByAlgorithm(userId: String): Response<BoardResponse> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.getBoardDataByAlgorithm(userId)
        }
    }

    suspend fun addComment(
        commentRequest: CommentRequest,
        boardId: String
    ): Response<Unit> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.addComment(
                boardId,
                commentRequest
            )
        }
    }

    suspend fun addBoard(boardRequest: BoardRequest): Response<Unit> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.addBoard(boardRequest)
        }
    }

}