package com.example.pathfinder.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {

    companion object{

        private val database = Firebase.database


        val boardRef = database.getReference("board")

        val teamBuildingRef = database.getReference("teamBuilding")

        val commentRef = database.getReference("comment")
    }
}