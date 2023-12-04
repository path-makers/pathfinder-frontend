package com.example.pathfinder.data.source.remote.board

import android.util.Log
import com.example.pathfinder.data.di.IoDispatcher
import com.example.pathfinder.data.response.model.Board

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class BoardDetailRemoteDataSource @Inject constructor(
    private val boardApi: BoardApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {


    suspend fun getBoardDataByType(boardType: String): Response<List<Board>> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.getBoardDataByType(boardType)

        }
    }

    suspend fun getBoardDataById(boardId: String): Response<Board> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.getBoardDataById(boardId)
        }
    }

    suspend fun getBoardDataByAlgorithm(userId: String): Response<List<Board>> {
        return withContext(ioDispatcher) {
            return@withContext boardApi.getBoardDataByAlgorithm(userId)
        }
    }








}