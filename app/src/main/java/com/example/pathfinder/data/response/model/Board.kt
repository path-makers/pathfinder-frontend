package com.example.pathfinder.data.response.model


import com.google.gson.annotations.SerializedName

data class Board(



    @SerializedName("author")
    val author: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("boardType")
    val boardType: String,

    @SerializedName("tags")
    val tags: List<String>,

    @SerializedName("comments")
    val comments: List<Comment>

    //todo:왜 여기에 작성하고 데이터 모델아래에는 작성하지 않는지?


)
