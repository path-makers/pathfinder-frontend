package com.example.pathfinder.data.source.remote.user

import com.example.pathfinder.data.di.IoDispatcher
import com.example.pathfinder.data.response.BoardResponse
import com.example.pathfinder.data.response.ProjectResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    suspend fun getUserBoardData(userId:String) : Response<BoardResponse> {
        return withContext(ioDispatcher){
            return@withContext userApi.getUserBoardData(userId)
        }


    }

    suspend fun getUserProjectData(userId:String) : Response<ProjectResponse> {
        return withContext(ioDispatcher){
            return@withContext userApi.getUserProjectData(userId)
        }


    }

}