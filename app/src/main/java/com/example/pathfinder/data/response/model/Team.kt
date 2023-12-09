package com.example.pathfinder.data.response.model

import com.google.gson.annotations.SerializedName

data class Team(

    @SerializedName("id")
    val id: String,
//    @SerializedName("uid")
//    val uid: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("createdAt")
    val uploadTime: String,
//    @SerializedName("comments")
//    val comments: List<Comment>

    //todo: 업로드 타임 한번더 확인,participants 추가해줄것

)
