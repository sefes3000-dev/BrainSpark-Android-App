package com.app.brainspark.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// إنشاء DataStore لحفظ البيانات محلياً (Offline Support)
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class UserPreferences(private val context: Context) {

    companion object {
        val STREAK_KEY = intPreferencesKey("streak")
        val COINS_KEY = intPreferencesKey("coins")
    }

    val streak: Flow<Int> = context.dataStore.data.map { it[STREAK_KEY] ?: 1 }
    val coins: Flow<Int> = context.dataStore.data.map { it[COINS_KEY] ?: 50 }

    suspend fun incrementStreak() {
        context.dataStore.edit { preferences ->
            val current = preferences[STREAK_KEY] ?: 1
            preferences[STREAK_KEY] = current + 1
        }
    }

    suspend fun addCoins(amount: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[COINS_KEY] ?: 50
            preferences[COINS_KEY] = current + amount
        }
    }
}
