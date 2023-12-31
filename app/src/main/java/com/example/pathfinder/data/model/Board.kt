package com.example.pathfinder.data.model

import java.io.Serializable

data class Board(
    val author:String="",
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val uid: String = "",
    val createdAt: String = "",
    val boardType: String = "",
    val tags: List<String> = listOf(),
    val comments: List<Comment> = listOf()
): Serializable