package com.example.kiememo.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Memo(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val body: String,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long
) {
    fun remainingMinutes(): Long =
        (expiresAt - System.currentTimeMillis()) / 1000 / 60
}
