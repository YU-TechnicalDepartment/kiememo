package com.example.kiememo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kiememo.data.Memo

@Composable
fun RemixScreen(
    original: Memo,
    onSave: (Memo) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(original.title) }
    var body by remember { mutableStateOf(original.body) }
    var minutes by remember { mutableStateOf("10") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("リミックス", fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("タイトル") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("本文") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = minutes,
            onValueChange = { minutes = it },
            label = { Text("消滅までの分数") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onBack) {
                Text("キャンセル")
            }
            Button(onClick = {
                val m = Memo(
                    title = title,
                    body = body,
                    expiresAt = System.currentTimeMillis() + minutes.toLong() * 60 * 1000
                )
                onSave(m)
            }) {
                Text("保存")
            }
        }
    }
}
