package com.example.pathfinder.data.response

import com.example.pathfinder.data.response.model.Team
import com.google.gson.annotations.SerializedName

data class TeamBuildingResponse(
    @SerializedName("projects")
    val teams: List<Team>
    //todo:한번 더 확인


)
