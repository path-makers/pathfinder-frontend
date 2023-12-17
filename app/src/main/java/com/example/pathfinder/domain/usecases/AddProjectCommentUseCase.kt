package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.data.repository.TeamBuildingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddProjectCommentUseCase @Inject constructor(
    private val teamBuildingRepository: TeamBuildingRepository
) {
    operator fun invoke(
        commentRequest: CommentRequest,
        boardId: String
    ): Flow<Results<Unit>> {
        return teamBuildingRepository.addComment(commentRequest,boardId)
    }


}

