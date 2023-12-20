package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Project
import com.example.pathfinder.data.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProjectDataUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
){

    operator fun invoke(): Flow<Results<List<Project>>> {
        return projectRepository.getTeamBuildingData()
    }
    //todo: 싱글톤 작성 이유
}