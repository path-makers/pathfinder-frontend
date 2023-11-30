package com.example.pathfinder.data.response.model

import com.google.gson.annotations.SerializedName

data class Comment(


    @SerializedName("author")
    val author: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("createdAt")
    val createdAt: Long




)
