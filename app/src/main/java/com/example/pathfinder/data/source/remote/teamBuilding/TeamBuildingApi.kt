package com.example.pathfinder.data.source.remote.teamBuilding

import com.example.pathfinder.data.response.TeamBuildingResponse
import com.example.pathfinder.data.response.model.Board
import com.example.pathfinder.data.response.model.Team
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamBuildingApi {
    @GET("/api/project/all")
    suspend fun getTeamData(): Response<TeamBuildingResponse>

    @GET("/api/project/single/{teamId}")
    suspend fun getSingleTeamData(@Path("teamId")teamId:String): Response<Team>

}