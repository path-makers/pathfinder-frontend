package com.example.pathfinder.data.mapper

import com.example.pathfinder.data.model.Team
import com.example.pathfinder.data.response.model.Team as ResponseTeam

fun responseTeamBuildingModelToDataModel(
    teamBuildingList: List<ResponseTeam>
): List<Team> {
    return teamBuildingList.map { team ->
        Team(
            id = team.id,
            author = team.author ?: "익명 유저",
            title = team.title,
            content = team.content,
            category = team.category,
            region = team.region,
            endTime = team.endTime,
            uploadTime = team.uploadTime,

            )
    }
}