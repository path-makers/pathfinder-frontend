package com.example.pathfinder.data.di

import com.example.pathfinder.data.source.remote.board.BoardApi
import com.example.pathfinder.data.source.remote.project.ProjectApi
import com.example.pathfinder.data.source.remote.user.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideBoardApi(retrofit: Retrofit): BoardApi {
        return retrofit.create(BoardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectApi(retrofit: Retrofit): ProjectApi {
        return retrofit.create(ProjectApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

}