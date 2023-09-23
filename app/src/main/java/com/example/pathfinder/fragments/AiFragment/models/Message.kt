package com.example.pathfinder.fragments.AiFragment.models

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

class Message(val mid:String,val mtext:String,var muser:IUser,var mdate:Date):IMessage{
    override fun getId(): String {
        return mid;
    }

    override fun getText(): String {
        return mtext;
    }

    override fun getUser(): IUser {
        return muser;
    }

    override fun getCreatedAt(): Date {
        return mdate;
    }


}