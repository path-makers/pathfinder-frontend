package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AddBoardCommentUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    operator fun invoke(
        commentRequest: CommentRequest,
        boardId: String
    ): Flow<Results<Unit>> {
        return boardRepository.addComment(commentRequest,boardId)
    }


}


