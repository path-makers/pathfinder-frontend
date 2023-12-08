package com.example.pathfinder.data.source.remote.teamBuilding

import com.example.pathfinder.data.response.TeamBuildingResponse
import retrofit2.Response
import retrofit2.http.GET

interface TeamBuildingApi {
    @GET("/api/project/all")
    suspend fun getTeamData(): Response<TeamBuildingResponse>
}