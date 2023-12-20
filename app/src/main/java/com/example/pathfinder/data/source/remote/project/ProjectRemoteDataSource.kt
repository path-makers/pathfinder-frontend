package com.example.pathfinder.data.source.remote.project

import com.example.pathfinder.data.di.IoDispatcher
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.response.ProjectResponse
import com.example.pathfinder.data.response.ProjectSingleResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ProjectRemoteDataSource @Inject constructor(
    private val projectApi: ProjectApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    suspend fun getTeamBuildingData(): Response<ProjectResponse> {
        return withContext(ioDispatcher){
            return@withContext projectApi.getTeamData()
        }
    }

    suspend fun getSingleTeamBuildingData(teamId:String): Response<ProjectSingleResponse> {
        return withContext(ioDispatcher){
            return@withContext projectApi.getSingleTeamData(teamId)
        }

    }


    suspend fun addComment(
        commentRequest: CommentRequest,
        projectId: String
    ): Response<Unit> {
        return withContext(ioDispatcher) {
            return@withContext projectApi.addComment(
                projectId,
                commentRequest
            )
        }
    }

    suspend fun addProject(projectRequest: ProjectRequest): Response<Unit> {
        return withContext(ioDispatcher) {
            return@withContext projectApi.addProject(projectRequest)
        }
    }

}