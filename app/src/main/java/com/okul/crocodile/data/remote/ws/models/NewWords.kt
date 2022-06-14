package com.okul.crocodile.data.remote.ws.models
import com.okul.crocodile.util.Constants

data class NewWords(
    val newWords: List<String>
): BaseModel(Constants.TYPE_NEW_WORDS)
