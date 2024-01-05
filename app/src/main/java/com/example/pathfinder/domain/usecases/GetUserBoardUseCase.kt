package com.example.pathfinder.domain.usecases

import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserBoardUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(userId:String): Flow<Results<List<Board>>> {
        return userRepository.getUserBoardData(userId)
    }
}