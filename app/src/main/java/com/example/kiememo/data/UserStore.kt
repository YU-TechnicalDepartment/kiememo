package com.example.kiememo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userDataStore by preferencesDataStore("user_prefs")

class UserStore(private val context: Context) {

    companion object {
        private val NAME = stringPreferencesKey("name")
    }

    val nameFlow: Flow<String> = context.userDataStore.data.map { prefs ->
        prefs[NAME] ?: ""
    }

    suspend fun saveName(name: String) {
        context.userDataStore.edit { prefs ->
            prefs[NAME] = name
        }
    }
}
