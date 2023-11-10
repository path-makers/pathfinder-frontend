package com.example.pathfinder.data.models

import java.io.Serializable

class Comment (
    val author : String = "",
    val content : String = "",
    val uid : String = "",
    val createdAt: Long = System.currentTimeMillis(),
        ): Serializable