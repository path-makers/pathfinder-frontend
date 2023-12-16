package com.example.pathfinder.data.mapper

import com.example.pathfinder.data.model.Team
import com.example.pathfinder.data.response.TeamBuildingSingleResponse

fun responseSingleTeamBuildingModelToDataModel(
    team: TeamBuildingSingleResponse
): Team{
    return Team(
        id = team.team.id,
        author = team.team.author,
        title = team.team.title,
        content = team.team.content,
        category = team.team.category,
        region = team.team.region,
        endTime = team.team.endTime,
        uploadTime = team.team.uploadTime,
        comment = team.team.comments
    )


}