package com.example.mysolelife.board

data class BoardModel (
    val title : String="",
    val content : String="",
    val uid : String="",
    val time : String="",
    val email : String="",
    var displayName: String? = null
        )