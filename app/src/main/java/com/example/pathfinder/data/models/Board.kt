package com.example.pathfinder.data.models

import java.io.Serializable

data class Board(

    val id: String = "",
    val title: String = "",
    val content: String = "",
    val uid: String = "",
    val date: String = "",
    val boardType: String = "",
    val tags: List<String> = listOf(),
    val comments: List<Comment> = listOf()
): Serializable