package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.ProjectRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
){
    operator fun invoke(
        projectRequest: ProjectRequest
    ): Flow<Results<Unit>> {
        return projectRepository.addProject(projectRequest)
    }
}