package com.example.pathfinder.data.source.remote.board

import com.example.pathfinder.data.models.Board
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class BoardDetailRemoteDataSource @Inject constructor(
    private val boardApi: BoardApi
) {


    suspend fun getBoardDataByType(boardType: String): Response<List<Board>> {
        return boardApi.getBoardDataByType(boardType)
    }
}