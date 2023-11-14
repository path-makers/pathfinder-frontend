package com.example.pathfinder.data.models



data class ChatRoom(
    val id: String = "",
    val participants: List<String> = listOf(),
    val name: String = "채팅방"

)
