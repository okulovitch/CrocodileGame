package com.okul.crocodile.data.remote.ws.models

import com.okul.crocodile.util.Constants

data class GameError(
    val errorType: Int,
): BaseModel(Constants.TYPE_GAME_ERROR) {
    companion object {
        const val ERROR_ROOM_NOT_FOUND = 0
    }
}
