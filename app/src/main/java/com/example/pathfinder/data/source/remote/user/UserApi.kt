package com.example.pathfinder.data.source.remote.user

import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.response.BoardResponse
import com.example.pathfinder.data.response.ProjectResponse
import com.example.pathfinder.data.response.ProjectSingleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @GET("/api/user/{userId}/board")
    suspend fun getUserBoardData(@Path("userId") userId:String): Response<BoardResponse>

    @GET("/api/project/{userId}/project")
    suspend fun getUserProjectData(@Path("userId") userId: String): Response<ProjectResponse>




}