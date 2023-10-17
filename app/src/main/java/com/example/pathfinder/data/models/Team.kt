package com.example.pathfinder.data.models

data class Team  (
        val category : String="",
        val title : String="",
        val content : String="",
        val regionArea : String="",
        val recruitTime : String="",
        var displayName: String? = null,
        val uploadTime : String="",
        val likeCount : Int = 0,
        val commentCount : Int = 0


    )