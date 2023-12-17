package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.BoardRequest
import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.data.repository.TeamBuildingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddProjectUseCase @Inject constructor(
    private val teamBuildingRepository: TeamBuildingRepository
){
    operator fun invoke(
        projectRequest: ProjectRequest
    ): Flow<Results<Unit>> {
        return teamBuildingRepository.addProject(projectRequest)
    }
}