package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.data.repository.TeamBuildingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTeamDataUseCase @Inject constructor(
    private val teamBuildingRepository: TeamBuildingRepository
){

    operator fun invoke(): Flow<Results<List<Team>>> {
        return teamBuildingRepository.getTeamBuildingData()
    }
    //todo: 싱글톤 작성 이유
}