package com.example.pathfinder.data.models

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

class Dm(
    private var id: String = "",
    private var text: String = "",
    private var user: DmUser = DmUser(), // IUser 대신 DmUser를 사용
    private var date: Date = Date(),
    var senderId: String = "",
    var recipientId: String = "",
    var chatRoomId: String = ""
) : IMessage {
    override fun getId(): String = id
    override fun getText(): String = text
    override fun getUser(): IUser = user
    override fun getCreatedAt(): Date = date
}
