package com.example.pathfinder.data.repository

import com.example.pathfinder.data.mapper.responseSingleTeamBuildingModelToDataModel
import com.example.pathfinder.data.mapper.responseTeamBuildingModelToDataModel
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.data.source.remote.teamBuilding.TeamBuildingRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TeamBuildingRepository constructor(
    //todo:왜 인젝트 안하는지
    private val teamBuildingRemoteDataSource: TeamBuildingRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher

    ){
    fun getTeamBuildingData(): Flow<Results<List<Team>>> {
        return flow {
            emit(Results.Loading)
            val response = teamBuildingRemoteDataSource.getTeamBuildingData()
            val teamData = response.body()?.teams?.let { responseTeamBuildingModelToDataModel(it) }
            if (response.isSuccessful && teamData != null) {
                emit(Results.Success(teamData))
            } else {
                emit(Results.Failure(response.message()))
            }

        }.flowOn(ioDispatcher)
        //todo:왜 flowOn을 쓰는지

    }

    fun getSingleTeamBuildingData(teamId:String): Flow<Results<Team>> {
        return flow {
            emit(Results.Loading)
            val response = teamBuildingRemoteDataSource.getSingleTeamBuildingData(teamId)
            val teamData = response.body()?.let { responseSingleTeamBuildingModelToDataModel(it) }
            if (response.isSuccessful && teamData != null) {
                emit(Results.Success(teamData))
            } else {
                emit(Results.Failure(response.message()))
            }

        }.flowOn(ioDispatcher)

    }
}