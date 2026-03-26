package com.example.kiememo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kiememo.data.Memo

@Composable
fun MemoDetailScreen(
    memo: Memo,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(memo.title, fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))
        Text(memo.body, fontSize = 18.sp)
        Spacer(Modifier.height(24.dp))
        Text("あと${memo.remainingMinutes()}分で消滅", fontSize = 14.sp)

        Spacer(Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text("戻る")
        }
    }
}
