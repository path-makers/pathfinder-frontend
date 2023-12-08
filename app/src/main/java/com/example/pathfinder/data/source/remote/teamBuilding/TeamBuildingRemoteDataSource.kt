package com.example.pathfinder.data.source.remote.teamBuilding

import com.example.pathfinder.data.di.IoDispatcher
import com.example.pathfinder.data.response.TeamBuildingResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TeamBuildingRemoteDataSource @Inject constructor(
    private val teamBuildingApi: TeamBuildingApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    suspend fun getTeamBuildingData(): Response<TeamBuildingResponse> {
        return withContext(ioDispatcher){
            return@withContext teamBuildingApi.getTeamData()
        }
    }

}