package com.okul.crocodile.data.remote.ws.models
import com.okul.crocodile.util.Constants

data class JoinRoomHandshake(
    val username: String,
    val roomName: String,
    val clientId: String
) : BaseModel(Constants.TYPE_JOIN_ROOM_HANDSHAKE)
