package com.example.pathfinder.data.response


import com.example.pathfinder.data.response.model.Board
import com.example.pathfinder.data.response.model.Comment
import com.google.gson.annotations.SerializedName

data class BoardResponse(

    @SerializedName("boards")
    val boards: List<Board>

)
