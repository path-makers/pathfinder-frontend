package com.example.pathfinder.data.di

import com.example.pathfinder.data.source.remote.board.BoardApi
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

class ApiModule {

    @Provides
    @Singleton
    fun provideBoardApi(retrofit: Retrofit): BoardApi {
        return retrofit.create(BoardApi::class.java)
    }
}