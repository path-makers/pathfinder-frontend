package com.example.pathfinder.data.repository


import com.example.pathfinder.data.mapper.responseBoardListModelToDataModel
import com.example.pathfinder.data.mapper.responseBoardModelToDataModel

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.model.CommentRequest

import com.example.pathfinder.data.model.Results

import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class BoardRefactorRepository constructor(
    private val boardDetailRemoteDataSource: BoardDetailRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {
    fun getBoardDataByType(boardType: String): Flow<Results<List<Board>>> {
        return flow {
            emit(Results.Loading)
            val response = boardDetailRemoteDataSource.getBoardDataByType(boardType)
            val boards = response.body()?.boards?.let { responseBoardListModelToDataModel(it) }
            if (response.isSuccessful && boards != null) {
                emit(Results.Success(boards))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }



    fun getBoardDataById(boardId: String): Flow<Results<Board>> {
        return flow {
            emit(Results.Loading)
            val response = boardDetailRemoteDataSource.getBoardDataById(boardId)
            val board = response.body()?.let { responseBoardModelToDataModel(it) }
            if (response.isSuccessful && board!= null) {
                emit(Results.Success(board))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }

    fun getBoardDataByAlgorithm(userId: String): Flow<Results<List<Board>>> {
        return flow {
            emit(Results.Loading)
            val response = boardDetailRemoteDataSource.getBoardDataByAlgorithm(userId)
            val boards = response.body()?.boards?.let { responseBoardListModelToDataModel(it) }
            if (response.isSuccessful && boards != null) {
                emit(Results.Success(boards))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }


    fun addComment(commentRequest: CommentRequest,boardId: String): Flow<Results<Unit>> {
        return flow {
            emit(Results.Loading)
            val response = boardDetailRemoteDataSource.addComment(commentRequest,boardId)
            if (response.isSuccessful) {
                emit(Results.Success(Unit))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }






}