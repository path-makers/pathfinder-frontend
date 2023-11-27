package com.example.pathfinder.data.repository

import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import com.google.firebase.firestore.local.LruGarbageCollector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BoardRetrofitRepository @Inject constructor(
    private val boardDetailRemoteDataSource: BoardDetailRemoteDataSource
) {
    fun getBoardDataByType(boardType: String): Flow<LruGarbageCollector.Results<List<Board>>> {
        return flow {
            emit(Results.Loading)
            val response = boardRemoteDataSource.getBoardDataByType(boardType)
            if (response.isSuccessful && response.body() != null) {
                emit(Results.Success(response.body()!!))
            } else {
                emit(Results.Error(Exception("Error fetching data")))
            }
        }
    }
}