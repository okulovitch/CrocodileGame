package com.okul.crocodile.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.util.*

val Context.dataStore by preferencesDataStore("settings")

suspend fun DataStore<Preferences>.clientId(): String {
    val clientIDKey = stringPreferencesKey("clientId")
    val preferences = data.first()
    val clientIdExists = preferences[clientIDKey] != null
    return if (clientIdExists) {
        preferences[clientIDKey] ?: ""
    } else {
        val newClientId = UUID.randomUUID().toString()
        edit { settings ->
            settings[clientIDKey] = newClientId
        }
        newClientId
    }
}