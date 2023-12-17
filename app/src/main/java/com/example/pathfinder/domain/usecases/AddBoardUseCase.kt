package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.BoardRequest
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddBoardUseCase @Inject constructor(
    private val boardRepository: BoardRepository
){
    operator fun invoke(
        boardRequest: BoardRequest
    ): Flow<Results<Unit>> {
        return boardRepository.addBoard(boardRequest)
    }
}