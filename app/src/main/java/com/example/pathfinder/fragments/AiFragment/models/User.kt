package com.example.pathfinder.fragments.AiFragment.models

import com.stfalcon.chatkit.commons.models.IUser

class User(val idm:String,val namem:String,val avatarm:String):IUser {
    override fun getId(): String {
       return idm
    }

    override fun getName(): String {
        return namem
    }

    override fun getAvatar(): String {
        return avatarm
    }

}