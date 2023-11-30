package com.example.pathfinder.data.model

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

class Message(private val id:String, private val text:String, private var user:IUser, private var date:Date):IMessage{
    override fun getId(): String {
        return id;
    }

    override fun getText(): String {
        return text;
    }

    override fun getUser(): IUser {
        return user;
    }

    override fun getCreatedAt(): Date {
        return date;
    }


}