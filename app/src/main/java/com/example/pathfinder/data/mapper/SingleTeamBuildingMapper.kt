package com.example.pathfinder.data.mapper

import com.example.pathfinder.data.model.Project
import com.example.pathfinder.data.response.ProjectSingleResponse

fun responseSingleProjectModelToDataModel(
    project: ProjectSingleResponse
): Project{
    return Project(
        id = project.project.id,
        author = project.project.author,
        title = project.project.title,
        content = project.project.content,
        category = project.project.category,
        region = project.project.region,
        endTime = project.project.endTime,
        uploadTime = project.project.uploadTime,
        comment = project.project.comments
    )


}