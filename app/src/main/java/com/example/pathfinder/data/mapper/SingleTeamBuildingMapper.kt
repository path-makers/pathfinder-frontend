package com.example.pathfinder.data.mapper

import com.example.pathfinder.data.model.Project
import com.example.pathfinder.data.response.ProjectSingleResponse

fun responseSingleTeamBuildingModelToDataModel(
    team: ProjectSingleResponse
): Project{
    return Project(
        id = team.project.id,
        author = team.project.author,
        title = team.project.title,
        content = team.project.content,
        category = team.project.category,
        region = team.project.region,
        endTime = team.project.endTime,
        uploadTime = team.project.uploadTime,
        comment = team.project.comments
    )


}