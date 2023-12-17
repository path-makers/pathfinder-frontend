package com.example.pathfinder.data.source.remote.teamBuilding

import com.example.pathfinder.data.model.BoardRequest
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.response.TeamBuildingResponse
import com.example.pathfinder.data.response.TeamBuildingSingleResponse
import com.example.pathfinder.data.response.model.Board
import com.example.pathfinder.data.response.model.Team
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamBuildingApi {
    @GET("/api/project/all")
    suspend fun getTeamData(): Response<TeamBuildingResponse>

    @GET("/api/project/single/{teamId}")
    suspend fun getSingleTeamData(@Path("teamId")teamId:String): Response<TeamBuildingSingleResponse>


    @POST("/api/project/comment/{projectId}")
    suspend fun addComment(
        @Path("projectId") projectId: String,
        @Body commentRequest: CommentRequest
    ): Response<Unit>

    @POST("/api/project")
    suspend fun addProject(@Body projectRequest: ProjectRequest): Response<Unit>



}