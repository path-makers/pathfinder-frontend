package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Project
import com.example.pathfinder.data.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSingleProjectDataUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(teamId:String): Flow<Results<Project>> {
        return projectRepository.getSingleTeamBuildingData(teamId)
    }

}