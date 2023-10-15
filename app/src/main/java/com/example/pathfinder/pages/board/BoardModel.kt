package com.example.pathfinder.pages.board

import java.io.Serializable

data class BoardModel(

    val title: String = "",
    val content: String = "",
    val uid: String = "",
    val date: String = "",
    val boardType: String = "",
    val tags: List<String> = listOf(),
    val comments: List<String> = listOf()
): Serializable