package com.example.pathfinder.data.source.remote.teamBuilding

import com.example.pathfinder.data.di.IoDispatcher
import com.example.pathfinder.data.model.BoardRequest
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.response.TeamBuildingResponse
import com.example.pathfinder.data.response.TeamBuildingSingleResponse
import com.example.pathfinder.data.response.model.Team
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

    suspend fun getSingleTeamBuildingData(teamId:String): Response<TeamBuildingSingleResponse> {
        return withContext(ioDispatcher){
            return@withContext teamBuildingApi.getSingleTeamData(teamId)
        }

    }


    suspend fun addComment(
        commentRequest: CommentRequest,
        projectId: String
    ): Response<Unit> {
        return withContext(ioDispatcher) {
            return@withContext teamBuildingApi.addComment(
                projectId,
                commentRequest
            )
        }
    }

    suspend fun addProject(projectRequest: ProjectRequest): Response<Unit> {
        return withContext(ioDispatcher) {
            return@withContext teamBuildingApi.addProject(projectRequest)
        }
    }

}