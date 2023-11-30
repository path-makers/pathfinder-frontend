package com.example.pathfinder.data.response


import com.example.pathfinder.data.response.model.Comment
import com.google.gson.annotations.SerializedName

data class GetBoardDataMentorResponse(


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


)
