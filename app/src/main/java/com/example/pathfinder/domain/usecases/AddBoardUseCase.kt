package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.BoardRequest
import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRefactorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddBoardUseCase @Inject constructor(
    private val boardRefactorRepository: BoardRefactorRepository
){
    operator fun invoke(
        boardRequest: BoardRequest
    ): Flow<Results<Unit>> {
        return boardRefactorRepository.addBoard(boardRequest)
    }
}