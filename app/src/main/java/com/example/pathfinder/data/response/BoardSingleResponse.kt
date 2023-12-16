package com.example.pathfinder.data.response


import com.example.pathfinder.data.response.model.Board

import com.google.gson.annotations.SerializedName

data class BoardSingleResponse(

    @SerializedName("board")
    val board: Board
    //todo: 모델 확인

)
