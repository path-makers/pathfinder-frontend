package com.example.pathfinder.data.response

import com.example.pathfinder.data.response.model.Project
import com.google.gson.annotations.SerializedName

data class ProjectSingleResponse(

    @SerializedName("project")
    val project: Project

)
