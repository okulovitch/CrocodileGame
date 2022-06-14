package com.okul.crocodile.data.remote.ws.models

import com.okul.crocodile.util.Constants

data class RoundDrawInfo(
    val data: List<String>
): BaseModel(Constants.TYPE_CUR_ROUND_DRAW_INFO)
