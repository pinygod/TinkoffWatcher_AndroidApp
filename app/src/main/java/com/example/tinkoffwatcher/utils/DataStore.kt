package com.example.tinkoffwatcher.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tinkoffwatcher.data.LoginDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {
    companion object {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
    }

    private val Context.dataStore by preferencesDataStore(name = "settings")

    val userLoginDataFlow: Flow<LoginDataModel> = context.dataStore.data
        .map { preferences ->
            LoginDataModel(preferences[USERNAME], preferences[PASSWORD])
        }

    val userTokenFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_TOKEN] ?: ""
        }

    suspend fun clearSavedPreferences() {
        context.dataStore.edit { settings ->
            settings.clear()
        }
    }

    suspend fun updateUsername(username: String) {
        context.dataStore.edit { settings ->
            settings[USERNAME] = username
        }
    }

    suspend fun deleteUsername() {
        context.dataStore.edit { settings ->
            settings[USERNAME] = ""
        }
    }

    suspend fun updatePassword(password: String) {
        context.dataStore.edit { settings ->
            settings[PASSWORD] = password
        }
    }

    suspend fun deletePassword() {
        context.dataStore.edit { settings ->
            settings[PASSWORD] = ""
        }
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