package com.okul.crocodile.data.remote.ws.models
import com.okul.crocodile.util.Constants


data class ChatMessage(
    val from: String,
    val roomName: String,
    val message: String,
    val timestamp: Long
) : BaseModel(Constants.TYPE_CHAT_MESSAGE)
