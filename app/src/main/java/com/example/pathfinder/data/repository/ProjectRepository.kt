package com.example.pathfinder.data.repository

import com.example.pathfinder.data.mapper.responseSingleTeamBuildingModelToDataModel
import com.example.pathfinder.data.mapper.responseProjectModelToDataModel
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Project
import com.example.pathfinder.data.source.remote.project.ProjectRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProjectRepository constructor(
    //todo:왜 인젝트 안하는지
    private val projectRemoteDataSource: ProjectRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher

    ){
    fun getTeamBuildingData(): Flow<Results<List<Project>>> {
        return flow {
            emit(Results.Loading)
            val response = projectRemoteDataSource.getTeamBuildingData()
            val teamData = response.body()?.projects?.let { responseProjectModelToDataModel(it) }
            if (response.isSuccessful && teamData != null) {
                emit(Results.Success(teamData))
            } else {
                emit(Results.Failure(response.message()))
            }

        }.flowOn(ioDispatcher)
        //todo:왜 flowOn을 쓰는지

    }

    fun getSingleTeamBuildingData(teamId:String): Flow<Results<Project>> {
        return flow {
            emit(Results.Loading)
            val response = projectRemoteDataSource.getSingleTeamBuildingData(teamId)
            val teamData = response.body()?.let { responseSingleTeamBuildingModelToDataModel(it) }
            if (response.isSuccessful && teamData != null) {
                emit(Results.Success(teamData))
            } else {
                emit(Results.Failure(response.message()))
            }

        }.flowOn(ioDispatcher)

    }

    fun addComment(commentRequest: CommentRequest, projectId: String): Flow<Results<Unit>> {
        return flow {
            emit(Results.Loading)
            val response = projectRemoteDataSource.addComment(commentRequest,projectId)
            if (response.isSuccessful) {
                emit(Results.Success(Unit))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }

    fun addProject(projectRequest: ProjectRequest): Flow<Results<Unit>> {
        return flow {
            emit(Results.Loading)
            val response = projectRemoteDataSource.addProject(projectRequest)
            if (response.isSuccessful) {
                emit(Results.Success(Unit))
            } else {
                emit(Results.Failure(response.message()))
            }
        }.flowOn(ioDispatcher)
    }


}