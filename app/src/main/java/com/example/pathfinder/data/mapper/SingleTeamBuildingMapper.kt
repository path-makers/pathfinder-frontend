package com.example.pathfinder.data.mapper

import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.data.response.model.Team as ResponseTeam

fun responseSingleTeamBuildingModelToDataModel(
    team: ResponseTeam
): Team{
    return Team(
        id = team.id,
        author = team.author,
        title = team.title,
        content = team.content,
        category = team.category,
        region = team.region,
        endTime = team.endTime,
        uploadTime = team.uploadTime
    )


}