package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBoardDataMentorUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {
//    operator fun invoke(): Flow<List<Board>> {
//        return boardRepository.getBoardDataByType("MENTOR")
//    }
    //todo: 코루틴 코드 더 보기
}
