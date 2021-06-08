package com.example.tinkoffwatcher.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {
    companion object {
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    private val Context.dataStore by preferencesDataStore(name = "settings")

    val userTokenFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_TOKEN] ?: ""
        }

    suspend fun updateUserToken(token: String) {
        context.dataStore.edit { settings ->
            settings[USER_TOKEN] = token
        }
    }

    suspend fun deleteUserToken() {
        context.dataStore.edit { settings ->
            settings[USER_TOKEN] = ""
        }
    }
}