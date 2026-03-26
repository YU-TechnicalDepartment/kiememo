package com.example.kiememo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kiememo.data.Memo

@Composable
fun MemoListScreen(
    name: String,
    memos: List<Memo>,
    onAddClick: () -> Unit,
    onMemoClick: (Memo) -> Unit,
    onRemixClick: (Memo) -> Unit,
    onDeleteClick: (Memo) -> Unit,
    onQRShareClick: (Memo) -> Unit,
    onQRScanClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("こんにちは、${name}さん", fontSize = 24.sp)
                Text("自動で消えるメモ", fontSize = 14.sp)
            }
            Button(
                onClick = onAddClick,
                modifier = Modifier.size(64.dp)
            ) {
                Text("+", fontSize = 32.sp)
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(memos) { memo ->
                var menuExpanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onMemoClick(memo) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(memo.title, fontSize = 20.sp)
                            Text(
                                "あと${memo.remainingMinutes()}分で消滅",
                                fontSize = 12.sp
                            )
                        }

                        Box {
                            Text(
                                "⋮",
                                modifier = Modifier
                                    .clickable { menuExpanded = true }
                            )
                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("リミックス") },
                                    onClick = {
                                        menuExpanded = false
                                        onRemixClick(memo)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("削除") },
                                    onClick = {
                                        menuExpanded = false
                                        onDeleteClick(memo)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("QRコードで共有") },
                                    onClick = {
                                        menuExpanded = false
                                        onQRShareClick(memo)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                memos.firstOrNull()?.let { onQRShareClick(it) }
            }) {
                Text("QRコードで共有")
            }
            Button(onClick = onQRScanClick) {
                Text("QRコードを読み取る")
            }
        }
    }
}
