package com.example.pathfinder.data.source.remote.project

import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.response.ProjectResponse
import com.example.pathfinder.data.response.ProjectSingleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectApi {
    @GET("/api/project/all")
    suspend fun getTeamData(): Response<ProjectResponse>

    @GET("/api/project/single/{teamId}")
    suspend fun getSingleTeamData(@Path("teamId")teamId:String): Response<ProjectSingleResponse>


    @POST("/api/project/comment/{projectId}")
    suspend fun addComment(
        @Path("projectId") projectId: String,
        @Body commentRequest: CommentRequest
    ): Response<Unit>

    @POST("/api/project")
    suspend fun addProject(@Body projectRequest: ProjectRequest): Response<Unit>



}