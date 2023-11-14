package com.example.pathfinder.data.models



import com.stfalcon.chatkit.commons.models.IUser

class DmUser(
    private var id: String = "",
    private var name: String = "",
    private var avatar: String = ""
) : IUser {
    override fun getId(): String = id
    override fun getName(): String = name
    override fun getAvatar(): String = avatar
}
