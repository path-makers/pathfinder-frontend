package com.example.pathfinder.data.source.remote.board


import com.example.pathfinder.data.model.CommentRequest
import com.example.pathfinder.data.response.BoardResponse
import com.example.pathfinder.data.response.BoardSingleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface BoardApi {
    @GET("/api/board/all")
    suspend fun getBoardDataByType(@Query("boardType") boardType: String): Response<BoardResponse>
    @GET("/api/board/single/{boardId}")
    suspend fun getBoardDataById(@Path("boardId") boardId: String): Response<BoardSingleResponse>

    @GET("/api/board/recommend/{userId}")
    suspend fun getBoardDataByAlgorithm(@Path("userId") userId: String): Response<BoardResponse>

    @POST("/api/board/comment/{boardId}")
    suspend fun addComment(
        @Path("boardId") boardId: String,
        @Body commentRequest: CommentRequest
    ): Response<Unit>


}