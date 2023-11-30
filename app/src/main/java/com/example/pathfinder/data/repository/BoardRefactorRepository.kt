package com.example.pathfinder.data.repository

import com.example.pathfinder.data.mapper.responseBoardListModelToDataModel

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results

import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BoardRefactorRepository @Inject constructor(
    private val boardDetailRemoteDataSource: BoardDetailRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun getBoardDataByType(boardType: String): Flow<Results<List<Board>>> {
        return flow {
            emit(Results.Loading)
            val response = boardDetailRemoteDataSource.getBoardDataByType(boardType)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val data = responseBoardListModelToDataModel(boardList = body)
                emit(Results.Success(data))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }
}