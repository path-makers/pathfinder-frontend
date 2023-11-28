package com.example.pathfinder.data.repository
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.models.Results
import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BoardRefactorRepository @Inject constructor(
    private val boardDetailRemoteDataSource: BoardDetailRemoteDataSource
) {
    fun getBoardDataByType(boardType: String): Flow<Results<List<Board>>> {
        return flow {
            emit(Results.Loading)
            val response = boardDetailRemoteDataSource.getBoardDataByType(boardType)
            if (response.isSuccessful && response.body() != null) {
                emit(Results.Success(response.body()!!))
            } else {
                emit(Results.Failure(response.message()))
            }
        }
    }
}