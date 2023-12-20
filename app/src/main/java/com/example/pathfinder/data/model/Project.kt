package com.example.pathfinder.data.model


import java.io.Serializable

data class Project(
        val id: String = "",
        var author: String = "",
        val title: String = "",
        val content: String = "",
        val category: String = "",
        val region: String = "",
        val endTime: String = "",
        val uploadTime: String = "",
        val comment: List<Comment> = listOf()

) : Serializable
