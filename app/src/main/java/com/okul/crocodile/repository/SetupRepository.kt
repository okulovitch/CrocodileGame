package com.okul.crocodile.repository

import com.okul.crocodile.data.remote.responses.BasicApiResponse
import com.okul.crocodile.data.remote.ws.Room
import com.okul.crocodile.util.Resource

interface SetupRepository {

    suspend fun createRoom(room: Room): Resource<Unit>

    suspend fun getRooms(searchQuery: String): Resource<List<Room>>

    suspend fun joinRoom(userName: String, roomName: String): Resource<Unit>
}