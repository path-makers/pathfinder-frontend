package com.example.pathfinder.data.response.model


import com.google.gson.annotations.SerializedName

data class Board(

    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("boardType")
    val boardType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val date: String,
    @SerializedName("comments")
    val comments: List<Comment>

    //todo:왜 여기에 작성하고 데이터 모델아래에는 작성하지 않는지?


)
