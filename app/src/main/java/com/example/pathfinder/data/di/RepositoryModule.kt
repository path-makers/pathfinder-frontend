package com.example.pathfinder.data.di

import com.example.pathfinder.data.repository.BoardRefactorRepository
import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideBoardRefactorRepository(
        boardDetailRemoteDataSource: BoardDetailRemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): BoardRefactorRepository {
        return BoardRefactorRepository(
            boardDetailRemoteDataSource = boardDetailRemoteDataSource,
            ioDispatcher = ioDispatcher

        )
    }


}