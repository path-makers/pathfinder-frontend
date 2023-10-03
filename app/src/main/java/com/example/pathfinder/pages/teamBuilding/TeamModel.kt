package com.example.pathfinder.pages

data class TeamModel  (
        val category : String="",
        val title : String="",
        val content : String="",
        val recruitTime : String="",
        val region : String="",
        var displayName: String? = null,
        val uploadTime : String="",
        val likeCount : Int = 0,
        val commentCount : Int = 0


    )
