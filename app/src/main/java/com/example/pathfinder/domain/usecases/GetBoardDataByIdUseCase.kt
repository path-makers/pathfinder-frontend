package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetBoardDataByIdUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
    operator fun invoke(boardId:String): Flow<Results<Board>> {
        return boardRepository.getBoardDataById(boardId)
    }

}