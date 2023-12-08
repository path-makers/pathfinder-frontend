package com.example.pathfinder.data.model

import java.io.Serializable

data class Team(
        var author: String = "",
        val title: String = "",
        val content: String = "",
        val category: String = "",
        val region: String = "",
        val endTime: String = "",
        val uploadTime: String = "",

) : Serializable
