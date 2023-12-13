package com.example.pathfinder.data.response

import com.example.pathfinder.data.response.model.Team
import com.google.gson.annotations.SerializedName

data class TeamBuildingSingleResponse(

    @SerializedName("project")
    val team: Team

)
