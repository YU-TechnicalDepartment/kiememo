package com.example.kiememo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.memoDataStore by preferencesDataStore("memo_prefs")

@Serializable
data class MemoList(val list: List<Memo> = emptyList())

class MemoStore(private val context: Context) {

    companion object {
        private val MEMOS = stringPreferencesKey("memos")
    }

    val memosFlow: Flow<List<Memo>> = context.memoDataStore.data.map { prefs ->
        prefs[MEMOS]?.let {
            Json.decodeFromString<MemoList>(it).list
        } ?: emptyList()
    }

    suspend fun saveMemos(memos: List<Memo>) {
        context.memoDataStore.edit { prefs ->
            prefs[MEMOS] = Json.encodeToString(MemoList(memos))
        }
    }
}
