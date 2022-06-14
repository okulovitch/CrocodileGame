package com.okul.crocodile.data.remote.ws.models

import com.okul.crocodile.data.remote.ws.models.BaseModel
import com.okul.crocodile.util.Constants

data class ChosenWord(
    val chosenWord: String,
    val roomName: String
): BaseModel(Constants.TYPE_CHOSEN_WORD)
