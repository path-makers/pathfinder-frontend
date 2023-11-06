package com.example.pathfinder.data.models

import java.io.Serializable

class Comment (
    val content : String = "",
    val uid : String = "",
    val createdAt : String = "",
    val userName: String = "",
    val timeStamp: Long = System.currentTimeMillis()
        ): Serializable