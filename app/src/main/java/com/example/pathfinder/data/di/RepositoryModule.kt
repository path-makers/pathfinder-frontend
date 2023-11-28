package com.example.pathfinder.data.di

import com.example.pathfinder.data.repository.BoardRefactorRepository
import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideReviewDetailRepository(boardDetailRemoteDataSource: BoardDetailRemoteDataSource): BoardRefactorRepository {
        return BoardRefactorRepository(boardDetailRemoteDataSource = boardDetailRemoteDataSource)
    }
}