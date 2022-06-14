package com.okul.crocodile.data.remote.ws.models

import com.okul.crocodile.data.remote.ws.Room
import com.okul.crocodile.util.Constants

data class PhaseChange(
    var phase: Room.Phase?,
    var time: Long,
    val drawingPlayer: String?= null
): BaseModel(Constants.TYPE_PHASE_CHANGE)
