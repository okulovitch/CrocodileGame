package com.okul.crocodile.data.remote.ws.models

import com.okul.crocodile.util.Constants

data class GameState(
    val drawingPlayer: String,
    val word: String
): BaseModel(Constants.TYPE_GAME_STATE)
