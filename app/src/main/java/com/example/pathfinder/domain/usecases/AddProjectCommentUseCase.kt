package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddProjectCommentUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    operator fun invoke(
        commentRequest: CommentRequest,
        boardId: String
    ): Flow<Results<Unit>> {
        return projectRepository.addComment(commentRequest,boardId)
    }


}

