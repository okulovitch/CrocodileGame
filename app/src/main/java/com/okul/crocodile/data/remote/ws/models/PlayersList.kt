package com.okul.crocodile.data.remote.ws.models

import com.okul.crocodile.util.Constants.TYPE_PLAYERS_LIST

data class PlayersList(
    val players: List<PlayerData>
): BaseModel(TYPE_PLAYERS_LIST)
