package com.example.pathfinder.data.model

import com.example.pathfinder.utils.FBAuth

data class BoardRequest(
    val title: String = "",
    val content: String = "",
    val uid: String = "",
    val boardType: String = "",
    val tags: List<String> = listOf(),
    val author: String = ""
)
