package com.example.pathfinder.data.repository

import com.example.pathfinder.data.mapper.responseBoardListModelToDataModel
import com.example.pathfinder.data.mapper.responseProjectModelToDataModel
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Project
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.source.remote.user.UserRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getUserBoardData(userId:String): Flow<Results<List<Board>>> {
        return flow {
            emit(Results.Loading)
            val response = userRemoteDataSource.getUserBoardData(userId)
            val boards = response.body()?.boards?.let { responseBoardListModelToDataModel(it) }
            if (response.isSuccessful && boards != null) {
                emit(Results.Success(boards))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }

    fun getUserProjectData(userId:String): Flow<Results<List<Project>>> {
        return flow {
            emit(Results.Loading)
            val response = userRemoteDataSource.getUserProjectData(userId)
            val boards = response.body()?.projects?.let { responseProjectModelToDataModel(it) }
            if (response.isSuccessful && boards != null) {
                emit(Results.Success(boards))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }



}