package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRefactorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AddCommentUseCase @Inject constructor(
    private val boardRefactorRepository: BoardRefactorRepository
) {
    operator fun invoke(
        commentRequest: CommentRequest,
        boardId: String
    ): Flow<Results<Unit>> {
        return boardRefactorRepository.addComment(commentRequest,boardId)
    }


}


