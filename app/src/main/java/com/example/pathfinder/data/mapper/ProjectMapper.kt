package com.example.pathfinder.data.mapper

import com.example.pathfinder.data.model.Project
import com.example.pathfinder.utils.Common.Companion.formatDate
import com.example.pathfinder.utils.Common.Companion.formatEndDate
import com.example.pathfinder.data.response.model.Project as ResponseProject

fun responseProjectModelToDataModel(
    projectList: List<ResponseProject>
): List<Project> {
    return projectList.map { project ->
        Project(
            id = project.id,
            author = project.author ?: "익명 유저",
            title = project.title,
            content = project.content,
            category = project.category,
            region = project.region,
            endTime = formatEndDate(project.endTime.toLong()),
            uploadTime = formatDate(project.uploadTime.toLong()),

            )
    }
}

