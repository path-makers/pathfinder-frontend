package com.example.pathfinder.data.source.remote.board


import com.example.pathfinder.data.response.BoardResponse
import com.example.pathfinder.data.response.BoardSingleResponse
import com.example.pathfinder.data.response.model.Board
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BoardApi {
    @GET("/api/board/all")
    suspend fun getBoardDataByType(@Query("boardType") boardType: String): Response<BoardResponse>
    @GET("/api/board/single/{boardId}")
    suspend fun getBoardDataById(@Path("boardId") boardId: String): Response<BoardSingleResponse>

    @GET("/api/board/recommend/{userId}")
    suspend fun getBoardDataByAlgorithm(@Path("userId") userId: String): Response<BoardResponse>




}