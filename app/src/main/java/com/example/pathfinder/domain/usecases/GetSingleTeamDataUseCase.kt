package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.data.repository.TeamBuildingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSingleTeamDataUseCase @Inject constructor(
    private val teamBuildingRepository: TeamBuildingRepository
) {
    operator fun invoke(teamId:String): Flow<Results<Team>> {
        return teamBuildingRepository.getSingleTeamBuildingData(teamId)
    }

}