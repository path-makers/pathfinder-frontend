package com.example.pathfinder.data.di

import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.data.repository.ProjectRepository
import com.example.pathfinder.data.repository.UserRepository
import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import com.example.pathfinder.data.source.remote.project.ProjectRemoteDataSource
import com.example.pathfinder.data.source.remote.user.UserRemoteDataSource
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
    ): BoardRepository {
        return BoardRepository(
            boardDetailRemoteDataSource = boardDetailRemoteDataSource,
            ioDispatcher = ioDispatcher

        )
    }

    @Singleton
    @Provides
    fun provideProjectRemoteDataSource(
        projectRemoteDataSource: ProjectRemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ProjectRepository {
        return ProjectRepository(
            projectRemoteDataSource = projectRemoteDataSource,
            ioDispatcher = ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UserRepository {
        return UserRepository(
            userRemoteDataSource = userRemoteDataSource,
            ioDispatcher = ioDispatcher
        )
    }


}