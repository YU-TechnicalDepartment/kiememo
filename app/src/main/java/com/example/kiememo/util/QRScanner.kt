package com.example.kiememo.util

import com.example.kiememo.data.Memo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun memoFromJson(json: String): Memo? =
    try {
        Json.decodeFromString<Memo>(json)
    } catch (e: Exception) {
        null
    }
