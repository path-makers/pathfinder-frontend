package com.example.pathfinder.data.response

import com.example.pathfinder.data.response.model.Project
import com.google.gson.annotations.SerializedName

data class ProjectResponse(
    @SerializedName("projects")
    val projects: List<Project>
    //todo:한번 더 확인


)
