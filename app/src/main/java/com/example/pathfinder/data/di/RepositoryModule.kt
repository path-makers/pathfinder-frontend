package com.example.pathfinder.data.di

import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.data.repository.TeamBuildingRepository
import com.example.pathfinder.data.source.remote.board.BoardDetailRemoteDataSource
import com.example.pathfinder.data.source.remote.teamBuilding.TeamBuildingRemoteDataSource
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
    fun provideTeamBuildingRemoteDataSource(
        teamBuildingRemoteDataSource: TeamBuildingRemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): TeamBuildingRepository {
        return TeamBuildingRepository(
            teamBuildingRemoteDataSource = teamBuildingRemoteDataSource,
            ioDispatcher = ioDispatcher
        )
    }


}