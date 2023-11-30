package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.BoardRefactorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBoardDataMentorUseCase @Inject constructor(
    private val boardRefactorRepository: BoardRefactorRepository
) {
    operator fun invoke(): Flow<Results<List<Board>>> {
        return boardRefactorRepository.getBoardDataByType("MENTOR")
    }

}
